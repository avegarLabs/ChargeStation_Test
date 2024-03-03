package org.avegarlabs.userservice.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avegarlabs.userservice.dto.ChargeStationUseResponse;
import org.avegarlabs.userservice.dto.UserListItem;
import org.avegarlabs.userservice.services.UserService;
import org.avegarlabs.userservice.util.ErrorMessage;
import org.avegarlabs.userservice.util.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/list")
    public ResponseEntity<Object> getUsers() {
        try {
            List<UserListItem> listItems = service.allUsers();
            return ResponseEntity.status(HttpStatus.OK).body(listItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeStation(@PathVariable String id) {
        try {
            service.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Successfully completed the operation."));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Object> validateToken(@RequestParam("id") String id) {
        try {
            UserListItem userListItem = service.getById(id);
            log.info("checking User : " + userListItem.getName());
            return ResponseEntity.status(HttpStatus.OK).body(userListItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }
    @GetMapping("/activity/{id}")
    public ResponseEntity<Object> userActivity(@PathVariable String id) {
        try {
            List<ChargeStationUseResponse> userChargesList = service.getChargesByUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(userChargesList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @GetMapping("/details/{moniker}")
    public ResponseEntity<Object> findByMoniker(@PathVariable String moniker) {
        try {
            UserListItem user = service.getByMoniker(moniker);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

 }
