package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.Repository.ClassesRepository;
import com.MapView.BackEnd.Repository.ResponsibleRepository;
import com.MapView.BackEnd.Repository.UserRepository;
import com.MapView.BackEnd.Service.ResponsibleService;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleUpdateDTO;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsibleServiceImp implements ResponsibleService {

    private final ResponsibleRepository responsibleRepository;
    private final ClassesRepository classesRepository;
    private final UserRepository userRepository;

    public ResponsibleServiceImp(ResponsibleRepository responsibleRepository, ClassesRepository classesRepository, UserRepository userRepository) {
        this.responsibleRepository = responsibleRepository;
        this.classesRepository = classesRepository;
        this.userRepository = userRepository;
    }
    @Override
    public ResponsibleDetailsDTO createResposible(ResponsibleCrateDTO data) {
        var user = userRepository.findById(data.id_user()).orElseThrow(() -> new NotFoundException("User Id Not Found"));
        var classe = classesRepository.findById(data.id_classes()).orElseThrow(() -> new NotFoundException("Class Id Not Found"));


        var responsible = new Responsible(data.responsible_name(), data.edv(), classe,user);
        responsibleRepository.save(responsible);
        return new ResponsibleDetailsDTO(responsible);

    }

    @Override
    public ResponsibleDetailsDTO getResposible(Long id_Resposible) {
        var responsible = responsibleRepository.findById(id_Resposible).orElseThrow(() -> new NotFoundException("Responsible Id Not Found"));
        return new ResponsibleDetailsDTO(responsible);

    }

    @Override
    public List<ResponsibleDetailsDTO> getAllResposible() {
        return responsibleRepository.findByOperativeTrue().stream().map(ResponsibleDetailsDTO::new).toList();
    }

    @Override
    public ResponsibleDetailsDTO updateResposible(Long id_responsible, ResponsibleUpdateDTO data) {
        var responsible = responsibleRepository.findById(id_responsible).orElseThrow(() -> new NotFoundException("Responsible Id Not Found"));
        var userEntity = userRepository.findById(data.id_user()).orElseThrow(() -> new NotFoundException("Responsible Id Not Found"));
        var classeEntity = classesRepository.findById(data.id_classes()).orElseThrow(() -> new NotFoundException("Responsible Id Not Found"));

        if(data.responsible_name() != null){
            responsible.setResponsible_name(data.responsible_name());
        }
        if(data.edv() != null){
            responsible.setEdv(data.edv());
        }
        if(data.edv() != null){
            responsible.setEdv(data.edv());
        }
        if(data.id_classes() != null){
            responsible.setId_classes(classeEntity);
        }
        if(data.id_user() != null){
            responsible.setId_user(userEntity);
        }
        responsibleRepository.save(responsible);
        return new ResponsibleDetailsDTO(responsible);

    }

    @Override
    public void activeResposible(Long id_resposible) {
        var responsible = responsibleRepository.findById(id_resposible).orElseThrow(() -> new NotFoundException("Responsible Id Not Found"));
        responsible.activeResposible();
        responsibleRepository.save(responsible);

    }

    @Override
    public void inactivateEnviroment(Long id_resposible) {
        var responsible = responsibleRepository.findById(id_resposible).orElseThrow(() -> new NotFoundException("Responsible Id Not Found"));
        responsible.inactivateEnviroment();
        responsibleRepository.save(responsible);

    }
}
