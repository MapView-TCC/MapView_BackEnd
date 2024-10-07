package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.infra.BlankErrorException;
import com.MapView.BackEnd.infra.OperativeFalseException;
import com.MapView.BackEnd.infra.OpetativeTrueException;
import com.MapView.BackEnd.repository.EnviromentRepository;
import com.MapView.BackEnd.repository.RaspberryRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.EnviromentService;
import com.MapView.BackEnd.dtos.Environment.EnviromentCreateDTO;
import com.MapView.BackEnd.dtos.Environment.EnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.Environment.EnviromentUpdateDTO;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class EnviromentServiceImp implements EnviromentService {

    private final EnviromentRepository enviromentRepository;
    private final RaspberryRepository raspberryRepository;
    private final UserLogRepository userLogRepository;

    private final UserRepository userRepository;

    public EnviromentServiceImp(EnviromentRepository enviromentRepository, RaspberryRepository raspberryRepository, UserLogRepository userLogRepository, UserRepository userRepository) {
        this.enviromentRepository = enviromentRepository;
        this.raspberryRepository = raspberryRepository;
        this.userLogRepository = userLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EnviromentDetailsDTO getEnviroment(Long enviroment_id, Long userLog_id) {
        var enviroment = enviromentRepository.findById(enviroment_id).orElseThrow(() -> new NotFoundException("Environment Id Not Found"));

        if(!enviroment.isOperative()){
            throw new OperativeFalseException("Id Inactive");
        }

        var id_user = userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User Id Not Found"));
        var userLog = new UserLog(id_user,"Environment",enviroment_id.toString(),"Read Environment", EnumAction.READ);
        userLogRepository.save(userLog);

        return new EnviromentDetailsDTO(enviroment);
    }

    @Override
    public List<EnviromentDetailsDTO> getAllEnviroment(int page, int itens, Long userLog_id) {
        var id_user = userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User Id Not Found"));
        var userLog = new UserLog(id_user,"Environment","Read All Environment", EnumAction.READ);
        userLogRepository.save(userLog);

        return this.enviromentRepository.findEnviromentByOperativeTrue(PageRequest.of(page, itens)).stream().map(EnviromentDetailsDTO::new).toList();
    }

    @Override
    public EnviromentDetailsDTO createEnviroment(EnviromentCreateDTO data, Long userLog_id) {
        var rasp = raspberryRepository.findById(data.id_raspberry()).orElseThrow(()->new NotFoundException("Id Raspberry Not Found"));
        var enviroment = new Enviroment(data, rasp);

        Long id_enviroment = enviromentRepository.save(enviroment).getId_enviroment();

        Users users = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found!"));

        var userLog = new UserLog(users,"Environment", id_enviroment.toString(),"Create new Environment", EnumAction.CREATE);
        userLogRepository.save(userLog);

        return new EnviromentDetailsDTO(enviroment);
    }

    @Override
    public EnviromentDetailsDTO updateEnviroment(Long enviroment_id,EnviromentUpdateDTO data, Long userLog_id) {
        var user = userRepository.findById(userLog_id).orElseThrow(()->new NotFoundException("Id Environment Not Found"));

        var enviroment = enviromentRepository.findById(enviroment_id).orElseThrow(()->new NotFoundException("Id Environment Not Found"));
        if(!enviroment.isOperative()){
            throw new OperativeFalseException("The inactive environment cannot be updated.");
        }

        var userlog = new UserLog(user,"Environment",enviroment_id.toString(),null,"Update Environment: ",EnumAction.UPDATE);

        if (data.enviroment_name() != null){
            if(data.enviroment_name().isBlank()){
                throw new BlankErrorException("Environment name cannot be blank");
            }
            enviroment.setEnviroment_name(data.enviroment_name());
            userlog.setField("environment_name");
            userlog.setDescription("environment_name to: " + data.enviroment_name());
        }
        if (data.id_raspberry() != null){
            var rasp = raspberryRepository.findById(data.id_raspberry()).orElseThrow(()->new NotFoundException("Id Raspberry Not Found"));
            if(rasp.isOperative()){
                throw new OperativeFalseException("The inactive raspberry cannot be updated.");
            }
            enviroment.setRaspberry(rasp);
            userlog.setField("id_raspberry");
            userlog.setDescription("id_raspberry to: ");
        }

        enviromentRepository.save(enviroment);
        userLogRepository.save(userlog);


        return new EnviromentDetailsDTO(enviroment);
    }

    @Override
    public void activateEnviroment(Long id_environment, Long userLog_id) {
        var enviroment = enviromentRepository.findById(id_environment).orElseThrow(()->new NotFoundException("Id Environment Not Found"));
        if(enviroment.isOperative()){
            throw new OpetativeTrueException("It is already activate");
        }
        enviroment.setOperative(true);
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var userLog = new UserLog(user,"Environment",id_environment.toString(),"Operative","Activated Environment",EnumAction.UPDATE);
        enviromentRepository.save(enviroment);
        userLogRepository.save(userLog);


    }

    @Override
    public void inactivateEnviroment(Long id_environment, Long userLog_id) {
        var enviroment = enviromentRepository.findById(id_environment).orElseThrow(()->new NotFoundException("Id Environment Not Found"));
        if(!enviroment.isOperative()){
            throw new OperativeFalseException("It is already inactivate");
        }
        enviroment.setOperative(false);
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var userLog = new UserLog(user,"Environment",id_environment.toString(),"Operative","Inactivated Environment",EnumAction.UPDATE);
        enviromentRepository.save(enviroment);
        userLogRepository.save(userLog);




    }

}
