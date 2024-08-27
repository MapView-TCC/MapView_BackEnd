package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.repository.CostCenterRepository;
import com.MapView.BackEnd.repository.MainOwnerRepository;
import com.MapView.BackEnd.service.MainOwnerService;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerUpdateDTO;
import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.entities.MainOwner;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class MainOwnerServiceImp implements MainOwnerService {


    private final MainOwnerRepository mainOwnerRepository;


    private final CostCenterRepository costCenterRepository;

    public MainOwnerServiceImp(MainOwnerRepository mainOwnerRepository, CostCenterRepository costCenterRepository) {
        this.mainOwnerRepository = mainOwnerRepository;
        this.costCenterRepository = costCenterRepository;
    }

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
    public List<MainOwnerDetailsDTO> getAllMainOwner(int page, int itens) {
        return mainOwnerRepository.findAllByOperativeTrue(PageRequest.of(page, itens)).stream().map(MainOwnerDetailsDTO::new).toList();
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
    public MainOwnerDetailsDTO updateMainOwner(String id_owner, MainOwnerUpdateDTO dados) {
        var mainowner = mainOwnerRepository.findById(id_owner).orElseThrow(() -> new NotFoundException("Main Owner id not found"));

        if (dados.owner_name() != null){
            mainowner.setOwner_name(dados.owner_name());
        }

        if (dados.id_cost_center() != null){
            var costcenter = costCenterRepository.findById(dados.id_cost_center()).orElseThrow(() -> new NotFoundException("Cost Center id not found"));
            mainowner.setId_cost_center(costcenter);
        }

        mainOwnerRepository.save(mainowner);
        return new MainOwnerDetailsDTO(mainowner);
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
