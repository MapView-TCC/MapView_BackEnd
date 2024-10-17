package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Equipment.EquipmentSearchBarDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentUpdateDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.enums.EnumColors;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.infra.Exception.*;
import com.MapView.BackEnd.repository.*;
import com.MapView.BackEnd.service.EquipmentService;
import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentServiceImp implements EquipmentService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final EquipmentRepository equipmentRepository ;
    private final LocationRepository locationRepository;
    private final MainOwnerRepository mainOwnerRepository;
    private final UserLogRepository userLogRepository;
    private final UserRepository userRepository;
    private final Path fileStorageLocation;
    private final TrackingHistoryRepository trackingHistoryRepository;
    private final ImageRepository imageRepository;


    public EquipmentServiceImp(EntityManager entityManager, EquipmentRepository equipmentRepository, LocationRepository locationRepository, MainOwnerRepository mainOwnerRepository,
                               UserLogRepository userLogRepository, UserRepository userRepository, FileStorageProperties fileStorageProperties, TrackingHistoryRepository trackingHistoryRepository, ImageRepository imageRepository) {
        this.entityManager = entityManager;
        this.equipmentRepository = equipmentRepository;
        this.locationRepository = locationRepository;
        this.mainOwnerRepository = mainOwnerRepository;
        this.userLogRepository = userLogRepository;
        this.userRepository = userRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.trackingHistoryRepository = trackingHistoryRepository;
        this.imageRepository = imageRepository;
    }


    @Override
    public EquipmentDetailsDTO getEquipment(String id_equipment, Long userLog_id) {
        var equipment = equipmentRepository.findById(String.valueOf(id_equipment)).orElseThrow(() -> new NotFoundException("Id equipment not found!"));
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));

        if (!equipment.isOperative()){
            throw new OperativeFalseException("The inactive responsible cannot be accessed.");
        }

        var userLog = new UserLog(user, "Equipment", id_equipment, "Read Equipment", EnumAction.READ);
        userLogRepository.save(userLog);
        return new EquipmentDetailsDTO(equipment);
    }

    @Override
    public List<EquipmentDetailsDTO>    getAllEquipment(int page, int itens, Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var userLog = new UserLog(user,"Equipment","Read All Equipment", EnumAction.READ);
        userLogRepository.save(userLog);

        return equipmentRepository.findAllByOperativeTrue(PageRequest.of(page, itens)).stream().map(EquipmentDetailsDTO::new).toList();
    }


    @Override
    public EquipmentDetailsDTO  createEquipment(EquipmentCreateDTO data, Long userLog_id) {
        Equipment verify = equipmentRepository.findByIdEquipmentAndOperativeTrue(data.id_equipment()).orElse(null);

        if (verify == null){
            try{

                Users users = this.userRepository.findById(userLog_id)
                        .orElseThrow(()  -> new NotFoundException("Id user_log ("+userLog_id+") not found!"));

                // Localização
                Location location = locationRepository.findById(Long.valueOf(data.id_location()))
                        .orElseThrow(() -> new RuntimeException("Id location ("+data.id_location()+ ") not found!"));

                // Proprietário principal
                MainOwner mainOwner = mainOwnerRepository.findById(String.valueOf(data.id_owner()))
                        .orElseThrow(() -> new RuntimeException("Id main owner " + data.id_owner() + ") not found!"));

                if (!mainOwner.isOperative()) {
                    throw new OperativeFalseException("The inactive mainOwner cannot be accessed.");
                }

                System.out.println(data.name_equipment());


                // Cria o equipamento


                Equipment equipment  = equipmentRepository.save(new Equipment(data,getStartDateFromQuarter(data.validity()), location, mainOwner));

                var userLog = new UserLog(users, "Equipment", data.id_equipment(), "Create new Equipment", EnumAction.CREATE);
                userLogRepository.save(userLog);

                // Salvar o tracking history
                Environment environment = location.getEnvironment();

                TrackingHistory trackingHistory = new TrackingHistory(
                        equipment, environment, equipment.getRfid(), EnumTrackingAction.ENTER,
                        EnumColors.GREEN
                );

                trackingHistoryRepository.save(trackingHistory);

                System.out.println(new EquipmentDetailsDTO(equipment));
                System.out.println("Post: Equipment ");
                return new EquipmentDetailsDTO(equipment);

            }catch (DataIntegrityViolationException e ){
                throw new ExistingEntityException("rfid: ("+ data.rfid()+") Already exists");
            }
        }
        throw new ExistingEntityException("Equipament: "+data.id_equipment()+ " Already exists");
    }


    @Override
    public EquipmentDetailsDTO updateEquipment(String id_equipment, EquipmentUpdateDTO data, Long userLog_id) {
        var equipment = equipmentRepository.findById(id_equipment)
                .orElseThrow(() -> new NotFoundException("Id not found"));

        if(!equipment.isOperative()){
            throw new OperativeFalseException("The inactive equipment cannot be updated.");
        }

        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var userlog = new UserLog(user,"Equipment",data.id_equipment(),null,"Infos update",EnumAction.UPDATE);

        if (data.id_equipment() != null){
            if(data.id_equipment().isBlank()){
                throw new BlankErrorException("Equipment id cannot be blank");
            }
            equipment.setIdEquipment(data.id_equipment());
            userlog.setField("equipment id to: " + data.id_equipment());
        }
        if (data.name_equipment() != null){
            if(data.name_equipment().isBlank()){
                throw new BlankErrorException("Equipment name cannot be blank");
            }
            equipment.setName_equipment(data.name_equipment());
            userlog.setField(userlog.getField()+" ,"+"equipment name to: " + data.name_equipment());
        }
        if (data.rfid() != 0) {
            equipment.setRfid(data.rfid());
            userlog.setField(userlog.getField()+" ,"+"equipment rfid to: " + data.rfid());
        }

        if (data.type() != null) {
            if(data.type().isBlank()){
                throw new BlankErrorException("Equipment type cannot be blank");
            }
            equipment.setType(data.type());
            userlog.setField(userlog.getField()+" ,"+"equipment type to: " + data.type());
        }

        if (data.model() != null) {
            equipment.setModel(data.model());
            userlog.setField(userlog.getField()+" ,"+"equipment model to: " + data.model());
        }

        if (data.validity() != null) {
            if(data.validity().isBlank()){
                throw new BlankErrorException("Equipment validity cannot be blank");
            }
            equipment.setValidity(getStartDateFromQuarter(data.validity()));
            userlog.setField(userlog.getField()+" ,"+"equipment validity to: " + data.validity());
        }

        if (data.admin_rights() != null) {
            if(data.admin_rights().isBlank()){
                throw new BlankErrorException("Admin rights cannot be blank");
            }
            equipment.setAdmin_rights(data.admin_rights());
            userlog.setField(userlog.getField()+" ,"+"equipment admin rights to: " + data.admin_rights());
        }

        if (data.observation() != null) {
            if(data.observation().isBlank()){
                throw new BlankErrorException("Observation cannot be blank");
            }
            equipment.setObservation(data.observation());
            userlog.setField(userlog.getField()+" ,"+"equipment observation to: " + data.observation());
        }

        if (data.id_location() != null) {
            var location = locationRepository.findById(data.id_location())
                    .orElseThrow(() -> new NotFoundException("Location id not found"));
            equipment.setLocation(location);
            userlog.setField(userlog.getField()+" ,"+"equipment location to: " + data.id_location());
        }

        if (data.id_owner() != null) {
            if(data.id_owner().isBlank()){
                throw new BlankErrorException("Owner id cannot be blank");
            }
            var owner = mainOwnerRepository.findById(data.id_owner())
                    .orElseThrow(() -> new NotFoundException("Owner id not found"));
            equipment.setOwner(owner);
            userlog.setField(userlog.getField()+" ,"+"equipment main owner to: " + data.id_owner());
        }

        userLogRepository.save(userlog);

        // Salva a entidade atualizada no repositório
        equipmentRepository.save(equipment);
        return new EquipmentDetailsDTO(equipment);
    }

    @Override
    public void activateEquipment(String id_equipment, Long userLog_id) {
        Users users = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var equipment = equipmentRepository.findById(id_equipment).orElseThrow(() -> new NotFoundException("Id not found"));

        if (equipment.isOperative()){
            throw new OpetativeTrueException("It is already activate");
        }

        equipment.setOperative(true);
        var userLog = new UserLog(users, "Equipment", id_equipment, "Operative", "Activated area", EnumAction.UPDATE);
        equipmentRepository.save(equipment);
        userLogRepository.save(userLog);
    }

    @Override
    public void inactivateEquipment(String id_equipment, Long userLog_id) {

        var equipment = equipmentRepository.findById(id_equipment).orElseThrow(() -> new NotFoundException("Id not found"));

        if (!equipment.isOperative()){
            throw new OperativeFalseException("It is already inactivate");
        }
        Users users = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        equipment.setOperative(false);
        var userLog = new UserLog(users, "Equipment", id_equipment, "Operative", "Inactivated area", EnumAction.UPDATE);
        equipmentRepository.save(equipment);
        userLogRepository.save(userLog);
    }

    @Override
    public List<EquipmentDetailsDTO> getEquipmentInventory(int page, int itens, String validity,
                                                            String environment, String id_owner, String id_equipment,
                                                            String name_equipment, String post) {




        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Equipment> criteriaQuery = criteriaBuilder.createQuery(Equipment.class);

        //Select From Equipment
        Root<Equipment> equipmentRoot = criteriaQuery.from(Equipment.class);

        //Inner Join
        Join<Equipment,MainOwner> mainOwnerJoin = equipmentRoot.join("owner");
        Join<Equipment, Location> locationJoin = equipmentRoot.join("location");
        Join<Equipment, Location> locationPostJoin = equipmentRoot.join("location");
        Join<Location, Post> PostJoin = locationPostJoin.join("post");
        Join<Location, Environment> environmentJoin = locationJoin.join("environment");


        List<Predicate> predicate = new ArrayList<>();
        System.out.println(predicate);

        //WHERE

        if(validity != null){
            LocalDate validDate = getStartDateFromQuarter(validity);
            predicate.add(criteriaBuilder.equal(equipmentRoot.get("validity"), validDate));
        }
        if (environment != null){
            predicate.add(criteriaBuilder.like(environmentJoin.get("environment_name"), "%"+environment.toLowerCase()+"%"));
        }
        if (id_owner != null){
            predicate.add(criteriaBuilder.like(mainOwnerJoin.get("id_owner"), "%"+id_owner.toLowerCase()+"%"));
        }
        if (id_equipment != null){
            predicate.add(criteriaBuilder.like(equipmentRoot.get("idEquipment"), "%" + id_equipment.toLowerCase() + "%"));
        }
        if (name_equipment != null){
            predicate.add(criteriaBuilder.like(equipmentRoot.get("name_equipment"), "%" + name_equipment.toLowerCase() + "%"));
        }
        if (post != null){
            predicate.add(criteriaBuilder.like(PostJoin.get("post"), "%" + post.toLowerCase() + "%"));
        }

        if (validity != null && environment != null && id_equipment != null && name_equipment != null && post != null){
            return equipmentRepository.findAllByOperativeTrue(PageRequest.of(page, itens))
                    .stream()
                    .map(EquipmentDetailsDTO::new)
                    .collect(Collectors.toList());
        }

        predicate.add(criteriaBuilder.equal(equipmentRoot.get("operative"), true));

        criteriaQuery.where(criteriaBuilder.and(predicate.toArray(new Predicate[0])));
        
        TypedQuery<Equipment> query = entityManager.createQuery((criteriaQuery));


        return query.getResultList().stream()
                .map(EquipmentDetailsDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<EquipmentSearchBarDTO> getEquipmentSearchBar(String searchTerm) {

        EquipmentResponsible equipmentResponsible = new EquipmentResponsible();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Equipment> criteriaQuery = criteriaBuilder.createQuery(Equipment.class);

        Root<Equipment> equipmentRoot = criteriaQuery.from(Equipment.class);

        Join<Equipment, MainOwner> mainOwnerJoin = equipmentRoot.join("owner");
        Join<Equipment, Location> locationJoin = equipmentRoot.join("location");
        Join<Location, Post> postJoin = locationJoin.join("post");
        Join<Location, Environment> environmentJoin = locationJoin.join("environment");

        List<Predicate> predicate = new ArrayList<>();


        // Se houver um termo de pesquisa, aplique-o a múltiplos campos
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            String searchLower = searchTerm.toLowerCase();
            Predicate searchPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(equipmentRoot.get("name_equipment")), "%" + searchLower + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(mainOwnerJoin.get("id_owner")), "%" + searchLower + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(equipmentRoot.get("idEquipment")), "%" + searchLower + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(postJoin.get("post")), "%" + searchLower + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(environmentJoin.get("environment_name")), "%" + searchLower + "%")
            );
            predicate.add(searchPredicate);
        }

        // Adiciona filtro para equipamentos operacionais
        predicate.add(criteriaBuilder.equal(equipmentRoot.get("operative"), true));

        criteriaQuery.where(criteriaBuilder.and(predicate.toArray(new Predicate[0])));

        TypedQuery<Equipment> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList().stream()
                .map(equipment -> {
                    // Obtém a localização e o proprietário
                    Location location = equipment.getLocation();
                    MainOwner mainOwner = equipment.getOwner();
                    Environment environment = equipment.getLocation().getEnvironment();

                    // Cria a lista de responsáveis
                    List<String> responsibles = equipment.getEquipmentResponsibles()
                            .stream()
                            .map(responsible -> responsible.getId_responsible().getResponsible()) // Altere para o campo desejado do responsável
                            .collect(Collectors.toList());

                    // Cria o DTO do equipamento
                    return new EquipmentSearchBarDTO(equipment, location, mainOwner,environment, responsibles);
                })
                .collect(Collectors.toList());
    }


    public ResponseEntity<String> uploadImageEquipament (MultipartFile file,EnumModelEquipment equipType){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);

            equipament_image(targetLocation,equipType);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/files/download/")
                    .path(fileName)
                    .toUriString();

            return ResponseEntity.ok("File uploaded successfully. Download link: " + fileDownloadUri);
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("File upload failed.");
        }

    }

    public void equipament_image(Path targetLocation, EnumModelEquipment equipmentModel){
        String targetLocatioString = targetLocation.toString();

        List<Equipment> allEquipments = equipmentRepository.findByModel(equipmentModel);

        Image image  = imageRepository.findByModel(equipmentModel);
        if(image==null){

            Image newimage = imageRepository.save(new Image(targetLocatioString,equipmentModel));
            for (Equipment equipment : allEquipments) {
                equipment.setId_image(newimage);
            }
        }

        image.setImage(targetLocatioString);
        imageRepository.save(image);



        equipmentRepository.saveAll(allEquipments);
    }

    public static LocalDate getStartDateFromQuarter(String quarterStr) {
        // Dividir a string em ano e trimestre
        String[] parts = quarterStr.split("\\.");
        int year = Integer.parseInt(parts[0]);
        int quarter = Integer.parseInt(parts[1].substring(1)); // Pega o número do trimestre

        // Calcular o primeiro mês do trimestre
        int month = (quarter - 1) * 3 + 1; // Q1 -> Janeiro, Q2 -> Abril, Q3 -> Julho, Q4 -> Outubro

        // Retornar a data do primeiro dia do mês do trimestre
        return LocalDate.of(year, month, 1);
    }

}

