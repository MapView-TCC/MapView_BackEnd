package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.ServiceImp.AccessHistoryServiceImp;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryCreateDTO;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryDetailsDTO;
import jakarta.transaction.Transactional;
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
    public AccessHistoryDetailsDTO create(@RequestBody AccessHistoryCreateDTO accessHistoryCreateDTO, UriComponentsBuilder uriBuilder){
        var history = accessHistoryServiceImp.createAccessHistory(accessHistoryCreateDTO);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/accessHistory/{id}").buildAndExpand(history.id_history()).toUri();
        return ResponseEntity.created(uri).body(new AccessHistoryDetailsDTO(history.id_history(), history.id_users(), history.login_dateTime(), history.logout_dateTime())).getBody();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AccessHistoryDetailsDTO> update(@PathVariable Long id){
        AccessHistoryDetailsDTO historyDetailsDTO = accessHistoryServiceImp.updateAccessHistory(id);
        return ResponseEntity.ok(historyDetailsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessHistoryDetailsDTO> getId(@PathVariable Long id){
        var accessHistory = accessHistoryServiceImp.getAccessHistory(id);
        return ResponseEntity.ok(accessHistory);
    }

    @GetMapping
    public ResponseEntity<List<AccessHistoryDetailsDTO>> getAll(){
        var list = accessHistoryServiceImp.getAllAccessHistory();
        return ResponseEntity.ok(list);
    }
}
