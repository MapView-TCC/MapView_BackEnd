package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Classes.ClassesCreateDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesDetaiLDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesUpdateDTO;
import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.infra.Exception.*;
import com.MapView.BackEnd.repository.ClassesRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.ClassesService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ClassesServiceImp implements ClassesService {

    private final ClassesRepository classesRepository;
    private final UserRepository userRepository;
    private final UserLogRepository userLogRepository;


    public ClassesServiceImp(ClassesRepository classesRepository, UserRepository userRepository, UserLogRepository userLogRepository) {
        this.classesRepository = classesRepository;
        this.userRepository = userRepository;
        this.userLogRepository = userLogRepository;
    }

    @Override
    public ClassesDetaiLDTO createClasses(ClassesCreateDTO data, Long userLog_id) {
        // Verifica se o usuário existe
        var usuario_log = userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        // Verifica se a classe já existe
        Classes verifyClasses = classesRepository.findByClasses(data.classes()).orElse(null);
        if (verifyClasses == null) {
            try {
                var user = userRepository.findById(data.user())
                        .orElseThrow(() -> new NotFoundException("User with ID " + data.user() + " not found"));

                // Criação da nova classe
                var classe = new Classes(data, user);
                Classes classes = classesRepository.save(classe);

                // Cria o log da ação do usuário
                var userLog = new UserLog(usuario_log, "Classes", classes.getId_classes().toString(), "Create new class", EnumAction.CREATE);
                userLogRepository.save(userLog);

                return new ClassesDetaiLDTO(classe);

            } catch (DataIntegrityViolationException e) {
                throw new ExistingEntityException("Class with name " + data.classes() + " already exists.");
            }
        }
        return new ClassesDetaiLDTO(verifyClasses);
    }

    @Override
    public ClassesDetaiLDTO getClasseById(Long id, Long userLog_id) {
        var usuario_log = userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        var classe = classesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Class with ID " + id + " not found"));

        if (!classe.isOperative()) {
            throw new OperativeFalseException("Cannot read the class area with ID " + id + " because it is inactive.");
        }

        var userLog = new UserLog(usuario_log, "Classes", id.toString(), "Read class", EnumAction.READ);
        userLogRepository.save(userLog);

        return new ClassesDetaiLDTO(classe);
    }

    @Override
    public ClassesDetaiLDTO getClasseByName(String class_name, Long userLog_id) {
        var usuario_log = userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        Classes classe = classesRepository.findByClasses(class_name).orElse(null);

        if (classe != null) {
            if (!classe.isOperative()) {
                throw new OperativeFalseException("Cannot read the class area with name " + class_name + " because it is inactive.");
            }
            var userLog = new UserLog(usuario_log, "Classes", class_name, "Read class", EnumAction.READ);
            userLogRepository.save(userLog);
            return new ClassesDetaiLDTO(classe);
        }
        return null;
    }

    @Override
    public ClassesDetaiLDTO updateClasses(Long classes_id ,ClassesUpdateDTO data, Long userLog_id) {
        var classes = classesRepository.findById(classes_id).orElseThrow(() -> new NotFoundException("Class with ID " + classes_id + " not found"));

        if(!classes.isOperative()){
            throw new OperativeFalseException("Cannot update the class with ID " + classes_id + " because it is inactive.");
        }

        var usuario_log = userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));
        var userlog = new UserLog(usuario_log, "Classes", classes_id.toString(), null, "Update Classes", EnumAction.UPDATE);

        if (data.user() != null) {
            var user = userRepository.findById(data.user())
                    .orElseThrow(() -> new NotFoundException("User with ID " + data.user() + " not found"));

            if (!user.isOperative()) {
                throw new OperativeFalseException("Cannot assign an inactive user with ID " + data.user() + " to this class.");
            }

            classes.setUser(user);
            userlog.setField("user");
            userlog.setDescription("Assigned user with ID " + data.user() + " to the class");
        }

        if (data.classes() != null && !data.classes().isBlank()) {
            classes.setClasses(data.classes());
            userlog.setField("classes");
            userlog.setDescription("classes to: " + data.classes());
        } else if (data.classes() != null) {
            throw new BlankErrorException("Class name cannot be blank.");
        }

        if (data.enumCourse() != null) {
            classes.setEnumCourse(data.enumCourse());
            userlog.setField("enumCourse");
            userlog.setDescription("enumCourse updated to: " + data.enumCourse());
        }

        classesRepository.save(classes);
        userLogRepository.save(userlog);

        return new ClassesDetaiLDTO(classes);
    }

    @Override
    public List<ClassesDetaiLDTO> getAllClasses(Long userLog_id) {
        var usuario_log = userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        var userLog = new UserLog(usuario_log, "Classes", "Read all classes", EnumAction.READ);
        userLogRepository.save(userLog);

        return this.classesRepository.findClassesByOperativeTrue().stream()
                .map(ClassesDetaiLDTO::new)
                .toList();
    }

    @Override
    public void activeClass(Long class_id, Long userLog_id) {
        var usuario_log = userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        // Verifica se o usuário está operativo
        if(!usuario_log.isOperative()) {
            throw new OperativeTrueException("User with ID " + userLog_id + " is inactive, cannot activate the class.");
        }

        var classes = classesRepository.findById(class_id)
                .orElseThrow(() -> new NotFoundException("Class with ID " + class_id + " not found"));
        classes.setOperative(true);
        classesRepository.save(classes);

        var userLog = new UserLog(usuario_log,"Classes",class_id.toString(),"Operative","Activated Classes",EnumAction.UPDATE);
        userLogRepository.save(userLog);
    }

    @Override
    public void inactiveClass(Long class_id, Long userLog_id) {
        var classes = classesRepository.findById(class_id).orElseThrow(() -> new NotFoundException("Class with ID " + class_id + " not found"));

        if(!classes.isOperative()) {
            throw new OperativeFalseException("It is already inactivate");
        }
        var usuario_log = userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));
        classes.setOperative(false);
        classesRepository.save(classes);

        var userLog = new UserLog(usuario_log,"Classes",class_id.toString(),"Operative","Inactivate Classes",EnumAction.UPDATE);
        userLogRepository.save(userLog);
    }

}
