package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Classes.ClassesCreateDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesDetaiLDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleCreateDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.Location.LocationCreateDTO;
import com.MapView.BackEnd.dtos.Location.LocationDetailsDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.dtos.Post.PostCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailsDTO;
import com.MapView.BackEnd.dtos.Register.RegisterCreateDTO;
import com.MapView.BackEnd.dtos.Register.RegisterDetailsDTO;
import com.MapView.BackEnd.dtos.Register.RegisterUpdateDTO;
import com.MapView.BackEnd.dtos.Register.ResponsibleResgisterDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.infra.Exception.ExistingEntityException;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.*;
import com.MapView.BackEnd.service.RegisterService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service

public class RegisterServiceImp implements RegisterService {

    private final PostRepository postRepository;
    private final EnvironmentRepository environmentRepository;
    private final EquipmentRepository equipmentRepository;
    private final LocationRepository locationRepository;
    private final UserLogRepository userLogRepository;
    private final UserRepository userRepository;
    private final MainOwnerRepository mainOwnerRepository;
    private final ResponsibleRepository responsibleRepository;
    private final ClassesRepository classesRepository;
    private final EquipmentServiceImp equipmentServiceImp;
    private final LocationServiceImp locationServiceImp;
    private final PostServiceImp postServiceImp;
    private final MainOwnerServiceImp mainOwnerServiceImp;
    private final CostCenterServiceImp costCenterServiceImp;
    private final ClassesServiceImp classesServiceImp;
    private final ResponsibleServiceImp responsibleServiceImp;
    private  final EquipmentResponsibleServiceImp equipmentResponsibleServiceImp;
    private  final CostCenterRepository costCenterRepository;


    public RegisterServiceImp(PostRepository postRepository,
                              EnvironmentRepository environmentRepository,
                              EquipmentRepository equipmentRepository,
                              LocationRepository locationRepository,
                              UserLogRepository userLogRepository,
                              UserRepository userRepository,
                              MainOwnerRepository mainOwnerRepository,
                              ResponsibleRepository responsibleRepository,
                              EquipmentResponsibleRepository equipmentResponsibleRepository,
                              ClassesRepository classesRepository, EquipmentServiceImp equipmentServiceImp,
                              LocationServiceImp locationServiceImp,
                              PostServiceImp postServiceImp,
                              EnvironmentServiceImp environmentServiceImp,
                              MainOwnerServiceImp mainOwnerServiceImp,
                              CostCenterServiceImp costCenterServiceImp,
                              ClassesServiceImp classesServiceImp,
                              ResponsibleServiceImp responsibleServiceImp, EquipmentResponsibleServiceImp equipmentResponsibleServiceImp, CostCenterRepository costCenterRepository) {

        this.postRepository = postRepository;
        this.environmentRepository = environmentRepository;
        this.equipmentRepository = equipmentRepository;
        this.locationRepository = locationRepository;
        this.userLogRepository = userLogRepository;
        this.userRepository = userRepository;
        this.mainOwnerRepository = mainOwnerRepository;
        this.responsibleRepository = responsibleRepository;
        this.classesRepository = classesRepository;
        this.equipmentServiceImp = equipmentServiceImp;
        this.locationServiceImp = locationServiceImp;
        this.postServiceImp = postServiceImp;
        this.mainOwnerServiceImp = mainOwnerServiceImp;
        this.costCenterServiceImp = costCenterServiceImp;
        this.classesServiceImp = classesServiceImp;
        this.responsibleServiceImp = responsibleServiceImp;
        this.equipmentResponsibleServiceImp = equipmentResponsibleServiceImp;
        this.costCenterRepository = costCenterRepository;
    }


    @Override
     public RegisterDetailsDTO register(RegisterCreateDTO data, Long userLog_id) {
        Users userlog = userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("This uses is incorrect"));


            PostDetailsDTO post = postServiceImp.createPost(new PostCreateDTO(data.post()), userLog_id);
            LocationDetailsDTO location = locationServiceImp.createLocation(new LocationCreateDTO(post.id_post(),data.id_environment()));
            CostCenterDetailsDTO costcenter = costCenterServiceImp.createCostCenter(new CostCenterCreateDTO(data.costCenter_name()),userLog_id);

            MainOwnerDetailsDTO owner = mainOwnerServiceImp.createMainOwner(new MainOwnerCreateDTO(data.id_owner(),costcenter.id_cost_center()),userLog_id);
            EquipmentDetailsDTO equipment = equipmentServiceImp.createEquipment(new EquipmentCreateDTO(
                    data.code(),
                    data.name_equipment(),
                    data.rfid(), data.type(),
                    data.model(),data.validity(),
                    data.admin_rights(),
                    data.observation(),
                    location.id_location(),
                    owner.id_owner()),
                    userLog_id);
             List<ResponsibleDetailsDTO> responsibleDetailsDTO = new ArrayList<>();
            if (data.dataResponsible() != null) {
                for (ResponsibleResgisterDTO listResponsible : data.dataResponsible()) {
                    ClassesDetaiLDTO newClasses = classesServiceImp.createClasses(new ClassesCreateDTO(listResponsible.enumCourse(), listResponsible.name_classes(), userLog_id, LocalDate.now()), userLog_id);

                    ResponsibleDetailsDTO responsible = responsibleServiceImp.createResposible(new ResponsibleCrateDTO(
                            listResponsible.responsible_name(),
                            listResponsible.edv(),
                            newClasses.id_classes(),
                            userLog_id), userLog_id);

                    responsibleDetailsDTO.add(responsible);
                    EquipmentResponsibleDetailsDTO equipmentResponsible = equipmentResponsibleServiceImp.createEquipmentResponsible(new EquipmentResponsibleCreateDTO(equipment.id_equipment(), responsible.responsible_id(), LocalDate.now(), LocalDate.now()));
                    UserlogCreate(userlog, "EquipmentResponsible", equipmentResponsible.id_equip_resp().toString(), "Create new EquipmentResponsible");

                }
            }

            return new RegisterDetailsDTO(equipment,location,responsibleDetailsDTO);
    }


    // metodo para fazer a atualização
    @Override
    public RegisterDetailsDTO updateRegister( RegisterUpdateDTO data,Long id_equipment, Long userLog_id) {
        // Encontrar e verificar o registro do usuário
        Users userLog = userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Obter o equipamento a ser atualizado
        Equipment equipment = equipmentRepository.findById(id_equipment)
                .orElseThrow(() -> new NotFoundException("Equipment not found"));

        // Atualizar dados básicos do Equipment
        if (data.name_equipment() != null) {
            if (data.name_equipment().length() < 8){
                throw new IllegalArgumentException("O nome do equipamento deve ser 8 ou maior caracteres.");
            }
            equipment.setName_equipment(data.name_equipment());
        }

        if (data.rfid() != null) {
            equipment.setRfid(data.rfid());
        }

        if (data.type() != null) {
            equipment.setType(data.type());
        }

        if (data.model() != null) {
            equipment.setModel(data.model());
        }

        if (data.validity() != null) {
            try {
                equipment.setValidity(getStartDateFromQuarter(data.validity()));
            }catch (IllegalArgumentException e ){
                throw new IllegalArgumentException("Fomato de data enviado errado");
            }

        }

        if (data.admin_rights() != null) {
            equipment.setAdmin_rights(data.admin_rights());
        }

        if (data.observation() != null) {
            equipment.setObservation(data.observation());
        }
        if (data.id_environment() != null){
            Location location = new Location();

            if (data.post() != null) {
            System.out.println(data.post());
                Post post = postRepository.findByPost(data.post()).orElse(null);
                System.out.println(post);
                if (post == null) {
                    post = postRepository.save(new Post(data.post()));
                }
                location.setPost(post);
            }

            Environment environment = environmentRepository.findById(data.id_environment())
                    .orElseThrow(() -> new NotFoundException("Environment not found"));
            location.setEnvironment(environment);


            Location location1= locationRepository.findByPostAndEnvironment(location.getPost(),location.getEnvironment()).orElse(null);

            if ( location1 != null){
                if (equipmentRepository.existsByLocation(location1)){
                    if (equipment.getLocation().getId_location() != location1.getId_location()){
                        Equipment equipmentByLocation = equipmentRepository.findByLocation(location1).orElseThrow(()-> new NotFoundException("Equipment by location not found"));
                        equipmentByLocation.setLocation(null);
                        equipmentRepository.save(equipmentByLocation);
                        equipment.setLocation(location1);
                    }
                    equipment.setLocation(location1);
                }
            } else {
                Location newLoc = locationRepository.save(location);
                equipment.setLocation(newLoc);
            }
        }




        if(data.costCenter_name() != null){
            CostCenter costCenter = costCenterRepository.findByCostCenter(data.costCenter_name()).orElse(null);
            if (data.costCenter_name() != equipment.getOwner().getCostCenter().getCostCenter()){


                if (costCenter == null) {
                    CostCenter newCostCenter = new CostCenter(new CostCenterCreateDTO(data.costCenter_name()));
                    equipment.getOwner().setCostCenter(costCenterRepository.save(newCostCenter));

                }
            }
        }

        if (data.id_owner() != null){
            if(data.id_owner() != equipment.getOwner().getCodOwner()){
                MainOwner mainOwner =  mainOwnerRepository.findByCodOwner(data.id_owner()).orElse(null);
                if (mainOwner == null){
                    MainOwner newMainOwner = mainOwnerRepository.save(new MainOwner(data.id_owner(),equipment.getOwner().getCostCenter()));
                    equipment.setOwner(newMainOwner);
                }
            }
        }



        if(data.dataResponsible() != null){

            for (ResponsibleResgisterDTO responsibleDTO : data.dataResponsible()) {
                Responsible responsible = responsibleRepository.findByEdv(responsibleDTO.edv())
                        .orElse(null);


                Classes classes = classesRepository.findByClasses(responsibleDTO.name_classes()).orElse(null);
                if (classes == null){
                    classes = classesRepository.save(new Classes(responsibleDTO.enumCourse(),responsibleDTO.name_classes(),userLog,LocalDate.now()));

                }else {
                    classes.setEnumCourse(responsibleDTO.enumCourse());
                    responsible.setClasses(classesRepository.save(classes));
                }


                if(responsible == null){
                    Responsible newResponsible = responsibleRepository.save(new Responsible(responsibleDTO.responsible_name(),responsibleDTO.edv(),classes,userLog));
                    equipmentResponsibleServiceImp.createEquipmentResponsible(new EquipmentResponsibleCreateDTO(equipment.getId_equipment(), newResponsible.getId_responsible(),LocalDate.now(),null));
                }

                responsibleRepository.save(responsible);
            }
        }
        equipmentRepository.save(equipment);
        return null;
    }



    public Location updateRegisterLocation(Location location,Long id_environment,String post){

        return  null;
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

    public void UserlogCreate(Users user, String tabela, String id,String descrption){

        var userLog = new UserLog(user,tabela,id,descrption, EnumAction.CREATE);
        userLogRepository.save(userLog);

    }
}