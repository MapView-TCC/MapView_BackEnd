package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.Repository.CostCenterRepository;
import com.MapView.BackEnd.Repository.MainOwnerRepository;
import com.MapView.BackEnd.Service.MainOwnerService;
import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.entities.MainOwner;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainOwnerServiceImp implements MainOwnerService {

    @Autowired
    private MainOwnerRepository mainOwnerRepository;

    @Autowired
    private CostCenterRepository costCenterRepository;

    @Override
    public MainOwnerDetailsDTO getMainOwner(String id_owner) {
        MainOwner mainOwner = this.mainOwnerRepository.findById(id_owner)
                .orElseThrow(() -> new NotFoundException("Id not found"));

        if (!mainOwner.status_check()) {
            throw new NotFoundException("MainOwner status is not valid");
        }

        return new MainOwnerDetailsDTO(mainOwner);
    }

    @Override
    public List<MainOwnerDetailsDTO> getAllMainOwner() {
        return mainOwnerRepository.findAllByOperativeTrue().stream().map(MainOwnerDetailsDTO::new).toList();
    }

    @Override
    public MainOwnerDetailsDTO createMainOwner(MainOwnerCreateDTO mainOwnerDTO) {

        CostCenter costCenter = costCenterRepository.findById(mainOwnerDTO.id_cost_center())
                .orElseThrow(() -> new RuntimeException("Não encontrado!"));


        MainOwner mainOwner = new MainOwner(mainOwnerDTO, costCenter);

        mainOwnerRepository.save(mainOwner);

        return new MainOwnerDetailsDTO(mainOwner);

    }

    @Override
    public void updateMainOwner(String owner_name, CostCenter id_cost_center) {

    }

    @Override
    public void activateMainOwner(String id_owner) {
        var mainOwnerClass = this.mainOwnerRepository.findById(id_owner);
        if (mainOwnerClass.isPresent()){
            var mainowner = mainOwnerClass.get();
            mainowner.setOperative(true);
            mainOwnerRepository.save(mainowner);
        }

    }

    @Override
    public void inactivateMainOwner(String id_owner) {
        var mainowner = this.mainOwnerRepository.findById(id_owner).orElseThrow(()-> new NotFoundException("id NOT FOUND"));
        mainowner.setOperative(false);
        mainOwnerRepository.save(mainowner);

    }
}
