package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.repository.EnviromentRepository;
import com.MapView.BackEnd.repository.RaspberryRepository;
import com.MapView.BackEnd.service.EnviromentService;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentCreateDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentUpdateDTO;
import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class EnviromentServiceImp implements EnviromentService {

    private final EnviromentRepository enviromentRepository;
    private final RaspberryRepository raspberryRepository;

    public EnviromentServiceImp(EnviromentRepository enviromentRepository, RaspberryRepository raspberryRepository) {
        this.enviromentRepository = enviromentRepository;
        this.raspberryRepository = raspberryRepository;
    }

    @Override
    public EnviromentDetailsDTO getEnvioment(Long enviroment_id) {
        var enviroment = enviromentRepository.findById(enviroment_id).orElseThrow(() -> new NotFoundException("Enviroment Id Not Found"));
        if(!status_check(enviroment)){
            return null;
        }
        return new EnviromentDetailsDTO(enviroment);
    }

    @Override
    public List<EnviromentDetailsDTO> getAllEnvioment() {
        return this.enviromentRepository.findEnviromentByOperativeTrue().stream().map(EnviromentDetailsDTO::new).toList();
    }

    @Override
    public EnviromentDetailsDTO createEnviroment(EnviromentCreateDTO data) {
        var rasp = raspberryRepository.findById(data.id_raspberry()).orElseThrow(()->new NotFoundException("Id Raspberry Not Found"));
        var enviroment = new Enviroment(data, rasp);
        enviromentRepository.save(enviroment);
        return new EnviromentDetailsDTO(enviroment);
    }

    @Override
    public EnviromentDetailsDTO updateEnviroment(Long enviroment_id,EnviromentUpdateDTO data) {
        var enviroment = enviromentRepository.findById(enviroment_id).orElseThrow(()->new NotFoundException("Id Enviroment Not Found"));
        var rasp = raspberryRepository.findById(data.id_raspberry()).orElseThrow(()->new NotFoundException("Id Raspberry Not Found"));
        if (data.environment_name() != null){
            enviroment.setEnvironment_name(data.environment_name());
        }
        if (data.id_raspberry() != null){
            enviroment.setId_raspberry(rasp);
        }
        enviromentRepository.save(enviroment);
        return new EnviromentDetailsDTO(enviroment);
    }

    @Override
    public void activateEnviroment(Long id_environment) {
        var enviroment = enviromentRepository.findById(id_environment).orElseThrow(()->new NotFoundException("Id Enviroment Not Found"));
        if(!status_check(enviroment)){
            enviroment.setOperative(true);
            enviromentRepository.save(enviroment);
        }



    }

    @Override
    public void inactivateEnviroment(Long id_environment) {
        var enviroment = enviromentRepository.findById(id_environment).orElseThrow(()->new NotFoundException("Id Enviroment Not Found"));
        if(status_check(enviroment)){
            enviroment.setOperative(false);
            enviromentRepository.save(enviroment);
        }


    }

    public boolean status_check(Enviroment enviroment){
        return enviroment.isOperative();

    }
}
