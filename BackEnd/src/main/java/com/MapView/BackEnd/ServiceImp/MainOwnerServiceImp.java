package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDTO;
import com.MapView.BackEnd.Repository.CostCenterRepository;
import com.MapView.BackEnd.Repository.MainOwnerRepository;
import com.MapView.BackEnd.Service.MainOwnerService;
import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.entities.MainOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainOwnerServiceImp implements MainOwnerService {

    @Autowired
    private MainOwnerRepository mainOwnerRepository;

    @Autowired
    private CostCenterRepository costCenterRepository;

    @Override
    public void getMainOwner(Long id_owner) {

    }

    @Override
    public void getAllMainOwner() {

    }

    @Override
    public void createMainOwner(MainOwnerDTO mainOwnerDTO) {
        CostCenter costCenter = costCenterRepository.findById(mainOwnerDTO.id_cost_center())
                .orElseThrow(() -> new RuntimeException("Não encontrado!"));

        costCenterRepository.save(costCenter);

        MainOwner mainOwner = new MainOwner(mainOwnerDTO, costCenter);

        mainOwnerRepository.save(mainOwner);

    }

    @Override
    public void updateMainOwner(String owner_name, CostCenter id_cost_center) {

    }

    @Override
    public void activateMainOwner(Long id_owner) {

    }

    @Override
    public void inactivateMainOwner(Long id_owner) {

    }
}
