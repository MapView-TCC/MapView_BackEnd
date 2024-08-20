package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDTO;
import com.MapView.BackEnd.ServiceImp.MainOwnerServiceImp;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/mainowner")
public class MainOwnerController {

    @Autowired
    private MainOwnerServiceImp mainOwnerServiceImp;

    @PostMapping
    @Transactional
    public ResponseEntity<MainOwnerDTO> cadastro(@RequestBody @Valid MainOwnerDTO mainOwnerDTO, UriComponentsBuilder uriBuilder){
        mainOwnerServiceImp.createMainOwner(mainOwnerDTO);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/mainowner/{id}").buildAndExpand(mainOwnerDTO.id_owner()).toUri();
        return ResponseEntity.created(uri).body(new MainOwnerDTO(mainOwnerDTO.id_owner(), mainOwnerDTO.owner_name(), mainOwnerDTO.id_cost_center()));
    }

}
