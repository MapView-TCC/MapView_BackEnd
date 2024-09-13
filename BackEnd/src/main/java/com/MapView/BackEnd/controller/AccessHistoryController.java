package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.AccessHistoryServiceImp;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryCreateDTO;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryDetailsDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accessHistory")
public class AccessHistoryController {


    private final AccessHistoryServiceImp accessHistoryServiceImp;

    public AccessHistoryController(AccessHistoryServiceImp accessHistoryServiceImp) {
        this.accessHistoryServiceImp = accessHistoryServiceImp;
    }

    @PostMapping
    @Transactional
    public AccessHistoryDetailsDTO createAccessHistory(@RequestBody @Valid AccessHistoryCreateDTO accessHistoryCreateDTO, UriComponentsBuilder uriBuilder){
        var history = accessHistoryServiceImp.createAccessHistory(accessHistoryCreateDTO);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/accessHistory/{id}").buildAndExpand(history.id_history()).toUri();
        return ResponseEntity.created(uri).body(new AccessHistoryDetailsDTO(history.id_history(), history.users(), history.login_dateTime(), history.logout_dateTime())).getBody();
    }

    @PutMapping("/{id_access_history}")
    @Transactional
    public ResponseEntity<AccessHistoryDetailsDTO> updateAccessHistory(@PathVariable Long id_access_history){
        AccessHistoryDetailsDTO historyDetailsDTO = accessHistoryServiceImp.updateAccessHistory(id_access_history);
        return ResponseEntity.ok(historyDetailsDTO);
    }

    @GetMapping("/{id_access_history}")
    public ResponseEntity<AccessHistoryDetailsDTO> getAccessHistory(@PathVariable Long id_access_history){
        var accessHistory = accessHistoryServiceImp.getAccessHistory(id_access_history);
        return ResponseEntity.ok(accessHistory);
    }

    @GetMapping
    public ResponseEntity<List<AccessHistoryDetailsDTO>> getAllAccessHistory(@RequestParam int page, @RequestParam int itens){
        var list = accessHistoryServiceImp.getAllAccessHistory(page,itens);
        return ResponseEntity.ok(list);
    }
}
