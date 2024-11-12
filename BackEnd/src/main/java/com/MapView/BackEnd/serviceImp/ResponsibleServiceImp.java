package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.infra.Exception.BlankErrorException;
import com.MapView.BackEnd.infra.Exception.OperativeFalseException;
import com.MapView.BackEnd.infra.Exception.OperativeTrueException;
import com.MapView.BackEnd.repository.ClassesRepository;
import com.MapView.BackEnd.repository.ResponsibleRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.ResponsibleService;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleUpdateDTO;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsibleServiceImp implements ResponsibleService {

    private final ResponsibleRepository responsibleRepository;
    private final ClassesRepository classesRepository;
    private final UserRepository userRepository;
    private final UserLogRepository userLogRepository;
    private final UserServiceImp userServiceImp;

    public ResponsibleServiceImp(ResponsibleRepository responsibleRepository, ClassesRepository classesRepository, UserRepository userRepository, UserLogRepository userLogRepository, UserServiceImp userServiceImp) {
        this.responsibleRepository = responsibleRepository;
        this.classesRepository = classesRepository;
        this.userRepository = userRepository;
        this.userLogRepository = userLogRepository;
        this.userServiceImp = userServiceImp;
    }

    @Override
    public ResponsibleDetailsDTO createResposible(ResponsibleCrateDTO data, Long userLog_id) {
        Users users = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id user not found"));
        Responsible verifyByName = responsibleRepository.findByResponsible(data.responsible()).orElse(null);

        if(verifyByName == null){
            var user = userRepository.findById(data.user()).orElseThrow(() -> new NotFoundException("User Id Not Found"));

            if(!user.isOperative()){
                throw new OperativeFalseException("The inactive User cannot be accessed.");
            }

            var classes = classesRepository.findById(data.classes()).orElseThrow(() -> new NotFoundException("Class Id Not Found"));
            if(!classes.isOperative()){
                throw new OperativeFalseException("The inactive Class cannot be accessed.");
            }

            var responsible = new Responsible(data.responsible(), data.edv(), classes,user);
            Long id_responsible = responsibleRepository.save(responsible).getId_responsible();

//            Users users1 = new Users();
//            BeanUtils.copyProperties(users, users1);

            var userLog = new UserLog(users, "Responsible", id_responsible.toString(), "Create new Responsible", EnumAction.CREATE);
            userLogRepository.save(userLog);

            return new ResponsibleDetailsDTO(responsible);
        }
        return new ResponsibleDetailsDTO(verifyByName);
    }

    @Override
    public ResponsibleDetailsDTO getResposibleById(Long id_Responsible, Long userLog_id) {


        var responsible = responsibleRepository.findById(id_Responsible).orElseThrow(() -> new NotFoundException("Responsible Id Not Found"));
        if (!responsible.isOperative()){
            throw new OperativeFalseException("The inactive responsible cannot be accessed.");
        }

        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var userLog = new UserLog(user, "Resposible", id_Responsible.toString(), "Read Resposible", EnumAction.READ);
        userLogRepository.save(userLog);

        return new ResponsibleDetailsDTO(responsible);

    }

    @Override
    public ResponsibleDetailsDTO getResposibleByEdv(String edv, Long userLog_id) {
        var responsible = responsibleRepository.findByEdv(edv).orElseThrow(() -> new NotFoundException("Responsible Id Not Found"));
        if (!responsible.isOperative()){
            throw new OperativeFalseException("The inactive responsible cannot be accessed.");
        }

        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var userLog = new UserLog(user, "Resposible", edv, "Read Resposible", EnumAction.READ);
        userLogRepository.save(userLog);

        return new ResponsibleDetailsDTO(responsible);

    }

    @Override
    public List<ResponsibleDetailsDTO> getAllResposible(Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var userLog = new UserLog(user,"Resposible","Read All Resposible", EnumAction.READ);
        userLogRepository.save(userLog);

        return responsibleRepository.findByOperativeTrue().stream().map(ResponsibleDetailsDTO::new).toList();
    }

    @Override
    public ResponsibleDetailsDTO updateResposible(Long id_Responsible, ResponsibleUpdateDTO data, Long userLog_id) {

        var responsible = responsibleRepository.findById(id_Responsible)
                .orElseThrow(() -> new NotFoundException("Responsible Id Not Found"));

        if (!responsible.isOperative()) {
            throw new OperativeFalseException("The inactive responsible cannot be accessed.");
        }

        Users userLog = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User Log Id Not Found"));
        var userlog = new UserLog(userLog, "Responsible", id_Responsible.toString(), null, "Info update", EnumAction.UPDATE);


        if (data.responsible() != null) {
            if(data.edv().isBlank()){
                throw new BlankErrorException("Responsible name cannot be blank");
            }
            responsible.setResponsible(data.responsible());
            userlog.setField("Responsible name updated to: " + data.responsible());
        }


        if (data.edv() != null) {
            if(data.edv().isBlank()){
                throw new BlankErrorException("Edv cannot be blank");
            }
            responsible.setEdv(data.edv());
            userlog.setField(userlog.getField() + " ,Responsible EDV updated to: " + data.edv());
        }


        if (data.classes() != null) {
            var classEntity = classesRepository.findById(data.classes())
                    .orElseThrow(() -> new NotFoundException("Class Id Not Found"));
            if (!classEntity.isOperative()) {
                throw new OperativeFalseException("The inactive classes cannot be accessed.");
            }
            responsible.setClasses(classEntity);
            userlog.setField(userlog.getField() + " ,Responsible class updated to: " + data.classes());
        }


        if (data.user() != null) {
            var user = userRepository.findById(data.user())
                    .orElseThrow(() -> new NotFoundException("User Id Not Found"));
            if (!user.isOperative()) {
                throw new OperativeFalseException("The inactive user cannot be accessed.");
            }
            responsible.setUser(user);
            userlog.setField(userlog.getField() + " ,Responsible user updated to: " + data.user());
        }

        userLogRepository.save(userlog);
        responsibleRepository.save(responsible);

        return new ResponsibleDetailsDTO(responsible);
    }


    @Override
    public void activeResposible(Long id_Responsible, Long userLog_id) {

        var responsible = responsibleRepository.findById(id_Responsible).orElseThrow(() -> new NotFoundException("Id not found"));
        if (responsible.isOperative()){
            throw new OperativeTrueException("It is already activate");
        }
        responsible.setOperative(true);
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var userLog = new UserLog(user, "Resposible", id_Responsible.toString(), "Operative", "Activated Resposible", EnumAction.UPDATE);
        responsibleRepository.save(responsible);
        userLogRepository.save(userLog);
    }

    @Override
    public void inactivateResposible(Long id_Responsible, Long userLog_id) {


        var responsibleClass = responsibleRepository.findById(id_Responsible).orElseThrow(()-> new NotFoundException("Responsible id not found"));
        if (!responsibleClass.isOperative()){
            throw new OperativeFalseException("It is already inactivate");
        }
        responsibleClass.setOperative(false);
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("Id not found"));
        var userLog = new UserLog(user, "Resposible", id_Responsible.toString(), "Operative", "Inactivated Resposible", EnumAction.UPDATE);
        responsibleRepository.save(responsibleClass);
        userLogRepository.save(userLog);
    }
}
