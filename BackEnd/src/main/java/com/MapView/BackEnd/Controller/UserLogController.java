package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.Service.UserLogService;

import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.dtos.UserLog.UserLogDetailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userlog")

public class UserLogController {

    private final UserLogService userLogService;

    public UserLogController(UserLogService userLogRepository, UserLogService userLogService) {
        this.userLogService = userLogService;
    }


    @GetMapping("/{userlog_id}")
    public UserLogDetailDTO getUserLog(@PathVariable("userlog_id") Long userlog_id){
        var userlog = userLogService.getUserLog(userlog_id);
        return ResponseEntity.ok(userlog).getBody();
    }

    @GetMapping
    public ResponseEntity<List<UserLogDetailDTO>> getAllUser(){
        var user = userLogService.getAllUserLog();
        return ResponseEntity.ok(user);
    }
}
