package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.infra.Exception.*;
import com.MapView.BackEnd.repository.*;
import com.MapView.BackEnd.service.RaspberryService;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryCreateDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryDetailsDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaspberryServiceImp implements RaspberryService {

    private final RaspberryRepository raspberryRepository;
    private final AreaRepository areaRepository;
    private final BuildingRepository buildingRepository;
    private final UserLogRepository userLogRepository;

    private final UserRepository userRepository;

    public RaspberryServiceImp(RaspberryRepository raspberryRepository, AreaRepository areaRepository, BuildingRepository buildingRepository, UserLogRepository userLogRepository, UserRepository userRepository) {
        this.raspberryRepository = raspberryRepository;
        this.areaRepository = areaRepository;
        this.buildingRepository = buildingRepository;
        this.userLogRepository = userLogRepository;
        this.userRepository = userRepository;
    }


    @Override
    public RaspberryDetailsDTO getRaspberry(String id_Raspberry, Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        Raspberry raspberry = this.raspberryRepository.findById(id_Raspberry)
                .orElseThrow(() -> new NotFoundException("Raspberry with ID " + id_Raspberry + " not found"));

        if (!raspberry.isOperative()){
            throw new OperativeFalseException("The inactive raspberry cannot be accessed.");
        }

        var userLog = new UserLog(user,"Raspberry",id_Raspberry.toString(),"Read Raspberry",EnumAction.READ);
        userLogRepository.save(userLog);

        return new RaspberryDetailsDTO(raspberry);
    }

    @Override
    public List<RaspberryDetailsDTO> getAllRaspberry(Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        var userLog = new UserLog(user,"Raspberry","Read All Raspberry", EnumAction.READ);
        userLogRepository.save(userLog);

        return raspberryRepository.findAllByOperativeTrue().stream().map(RaspberryDetailsDTO::new).toList();
    }

    @Override
    public RaspberryDetailsDTO createRaspberry(RaspberryCreateDTO data, Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        Raspberry existsVerifyRaspberry = raspberryRepository.findById(data.id_raspberry())
                .orElse(null);

        if(existsVerifyRaspberry == null){

            Building building = buildingRepository.findById(data.building())
                    .orElseThrow(() -> new NotFoundException("Building with ID " + data.building() + " not found."));

            if (!building.isOperative()) {
                throw new OperativeFalseException("The inactive building cannot be accessed.");
            }

            Area area = areaRepository.findById(data.area())
                    .orElseThrow(() -> new NotFoundException("Area with ID " + data.area() + " not found."));

            if (!area.isOperative()) {
                throw new OperativeFalseException("The inactive area cannot be accessed.");
            }

            Raspberry raspberry = new Raspberry(data, building, area);
            String id_raspberry = raspberryRepository.save(raspberry).getId_raspberry();

            var userLog = new UserLog(user, "Raspberry", id_raspberry, "Create new Raspberry", EnumAction.CREATE);
            userLogRepository.save(userLog);

            return new RaspberryDetailsDTO(raspberry);
        }
        return new RaspberryDetailsDTO(existsVerifyRaspberry);

    }

    @Override
    public RaspberryDetailsDTO updateRaspberry(String id_raspberry, RaspberryUpdateDTO data, Long userLog_id) {
        var raspberry = raspberryRepository.findById(id_raspberry)
                .orElseThrow(() -> new NotFoundException("Raspberry with ID " + id_raspberry + " not found"));

        if (!raspberry.isOperative()) {
            throw new OperativeFalseException("The inactive raspberry with ID " + id_raspberry + " cannot be accessed.");
        }

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        var userlog = new UserLog(user,"Raspberry",id_raspberry,null,"Update Raspberry: ",EnumAction.UPDATE);


        if (data.raspberry_name() != null){
            if(data.raspberry_name().isBlank()) {
                throw new BlankErrorException("Raspberry name Cannot be blank");
            }
            raspberry.setId_raspberry(data.raspberry_name());
            userlog.setField("raspberry_name");
            userlog.setDescription("userLog_id to: " + data.raspberry_name());
        }

        if (data.building() != null){
            var building = buildingRepository.findById(data.building())
                    .orElseThrow(() -> new NotFoundException("Building with ID " + data.building() + " not found"));

            raspberry.setBuilding(building);
            userlog.setField("id_building");
            userlog.setDescription("id_building to: " + data.building());
        }

        if (data.area() != null){
            var area = areaRepository.findById(data.area())
                    .orElseThrow(() -> new NotFoundException("Area with ID " + data.area() + " not found"));

            raspberry.setArea(area);
            userlog.setField("id_area");
            userlog.setDescription("id_area to: " + data.area());
        }

        raspberryRepository.save(raspberry);
        userLogRepository.save(userlog);

        return new RaspberryDetailsDTO(raspberry);

    }

    @Override
    public void activeRaspberry(String id_Raspberry, Long userLog_id) {
        var raspberry = this.raspberryRepository.findById(id_Raspberry)
                .orElseThrow(() -> new NotFoundException("Raspberry with ID " + id_Raspberry + " not found"));

        if (raspberry.isOperative()) {
            throw new OperativeTrueException("The raspberry is already active.");
        }

        var user = userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        raspberry.setOperative(true);
        var userLog = new UserLog(user, "Raspberry", id_Raspberry, "Operative", "Activated Raspberry", EnumAction.UPDATE);
        raspberryRepository.save(raspberry);
        userLogRepository.save(userLog);
    }

    @Override
    public void inactivateRaspberry(String id_Raspberry, Long userLog_id) {
        var raspberry = this.raspberryRepository.findById(id_Raspberry)
                .orElseThrow(() -> new NotFoundException("Raspberry with ID " + id_Raspberry + " not found"));

        if (!raspberry.isOperative()) {
            throw new OperativeFalseException("The raspberry is already inactive.");
        }

        raspberry.setOperative(false);
        var user = userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        var userLog = new UserLog(user, "Raspberry", id_Raspberry, "Operative", "Inactivated Raspberry", EnumAction.UPDATE);
        userLogRepository.save(userLog);
        raspberryRepository.save(raspberry);
    }
}
