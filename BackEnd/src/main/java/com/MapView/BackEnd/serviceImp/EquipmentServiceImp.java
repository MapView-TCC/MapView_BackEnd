package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Equipment.*;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.enums.EnumWarnings;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.infra.Exception.*;
import com.MapView.BackEnd.repository.*;
import com.MapView.BackEnd.service.EquipmentService;
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
    public EquipmentDetailsDTO getEquipment(String code, Long userLog_id) {
        var equipment = equipmentRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Id equipment not found!"));
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));

        if (!equipment.isOperative()){
            throw new OperativeFalseException("The inactive responsible cannot be accessed.");
        }

        var userLog = new UserLog(user, "Equipment", code, "Read Equipment", EnumAction.READ);
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
        Equipment verify = equipmentRepository.findByCodeAndOperativeTrue(data.code()).orElse(null);

        if (verify == null){
            try{

                Users users = this.userRepository.findById(userLog_id)
                        .orElseThrow(()  -> new NotFoundException("Id user_log ("+userLog_id+") not found!"));

                //

                Location location = locationRepository.findById(Long.valueOf(data.id_location()))
                        .orElseThrow(() -> new RuntimeException("Id location ("+data.id_location()+ ") not found!"));

                if(equipmentRepository.existsByLocation(location)){
                    throw new ExistingEntityException("equipamento ja atrelado a essa localização");
                }

                // Proprietário principal
                MainOwner mainOwner = mainOwnerRepository.findById(data.id_owner())
                        .orElseThrow(() -> new RuntimeException("Id main owner " + data.id_owner() + ") not found!"));

                if (!mainOwner.isOperative()) {
                    throw new OperativeFalseException("The inactive mainOwner cannot be accessed.");
                }

                System.out.println(data.name_equipment());


                // Cria o equipamento


                Equipment equipment  = equipmentRepository.save(new Equipment(data,getStartDateFromQuarter(data.validity()), location, mainOwner));

                var userLog = new UserLog(users, "Equipment", data.code(), "Create new Equipment", EnumAction.CREATE);
                userLogRepository.save(userLog);

                // Salvar o tracking history
                Environment environment = location.getEnvironment();

                TrackingHistory trackingHistory = new TrackingHistory(
                        equipment, environment, EnumTrackingAction.ENTER,
                        EnumWarnings.GREEN
                );

                trackingHistoryRepository.save(trackingHistory);

                System.out.println(new EquipmentDetailsDTO(equipment));
                System.out.println("Post: Equipment ");
                return new EquipmentDetailsDTO(equipment);

            }catch (DataIntegrityViolationException e ){
                throw new ExistingEntityException("rfid: ("+ data.rfid()+") Already exists");
            }
        }
        throw new ExistingEntityException("Equipment: " + data.code() + " Already exists");
    }


    @Override
    public EquipmentDetailsDTO updateEquipment(Long id_equipment, EquipmentUpdateDTO data, Long userLog_id) {
        var equipment = equipmentRepository.findById(id_equipment)
                .orElseThrow(() -> new NotFoundException("Id not found"));

        if(!equipment.isOperative()){
            throw new OperativeFalseException("The inactive equipment cannot be updated.");
        }

        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var userlog = new UserLog(user,"Equipment",data.code(),null,"Infos update",EnumAction.UPDATE);

        if (data.code() != null){
            if(data.code().isBlank()){
                throw new BlankErrorException("Equipment id cannot be blank");
            }
            equipment.setCode(data.code());
            userlog.setField("equipment id to: " + data.code());
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
    public void activateEquipment(String code, Long userLog_id) {
        Users users = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var equipment = equipmentRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Id not found"));

        if (equipment.isOperative()){
            throw new OpetativeTrueException("It is already activate");
        }

        equipment.setOperative(true);
        var userLog = new UserLog(users, "Equipment", code, "Operative", "Activated area", EnumAction.UPDATE);
        equipmentRepository.save(equipment);
        userLogRepository.save(userLog);
    }

    @Override
    public void inactivateEquipment(String code, Long userLog_id) {

        var equipment = equipmentRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Id not found"));

        if (!equipment.isOperative()){
            throw new OperativeFalseException("It is already inactivate");
        }
        Users users = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        equipment.setOperative(false);
        equipment.setLocation(null);
        var userLog = new UserLog(users, "Equipment", code, "Operative", "Inactivated area", EnumAction.UPDATE);
        equipmentRepository.save(equipment);
        userLogRepository.save(userLog);
    }

    @Override
    public List<EquipmentSearchBarDTO> getEquipmentSearchBar(String searchTerm) {

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
            String searchLower = searchTerm.toLowerCase(); // para que ele aceite letras maiuscula e minusculas
            Predicate searchPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(equipmentRoot.get("name_equipment")), "%" + searchLower + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(mainOwnerJoin.get("codOwner")), "%" + searchLower + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(equipmentRoot.get("code")), "%" + searchLower + "%"),
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
                    Environment environment = location.getEnvironment();

                    // Busca o último histórico de rastreamento
                    TrackingHistory trackingHistory = trackingHistoryRepository.findTop1ByEquipmentOrderByIdDesc(equipment);
                    String wrong = trackingHistory != null ? trackingHistory.getEnvironment().getEnvironment_name() : null;

                    // Cria a lista de responsáveis
                    List<String> responsibles = equipment.getEquipmentResponsibles()
                            .stream()
                            .map(responsible -> responsible.getResponsible().getResponsible()) // VOU TER QUE MUDAR AQUI getId_responsible
                            .collect(Collectors.toList());

                    // Cria o DTO do equipamento
                    return new EquipmentSearchBarDTO(equipment, location, mainOwner, environment, wrong, responsibles);
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

    public static String getQuarterStringFromDate(LocalDate date) {
        // Obter o ano
        int year = date.getYear();

        // Calcular o trimestre
        int month = date.getMonthValue();
        int quarter = (month - 1) / 3 + 1; // Q1 -> 1, Q2 -> 2, Q3 -> 3, Q4 -> 4

        // Formatar a string no formato "YYYY.QX"
        return year + ".Q" + quarter;
    }

}

