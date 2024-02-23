package org.avegarlabs.userservice.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avegarlabs.userservice.dto.UserListItem;
import org.avegarlabs.userservice.services.UserService;
import org.avegarlabs.userservice.util.ErrorMessage;
import org.avegarlabs.userservice.util.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getUsers() {
        try {
            List<UserListItem> listItems = service.allUsers();
            return ResponseEntity.status(HttpStatus.OK).body(listItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> removeStation(@PathVariable String id) {
        try {
            service.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Successfully completed the operation."));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @GetMapping("/check")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> validateToken(@RequestParam("id") String id) {
        try {
            UserListItem userListItem = service.getById(id);
            log.info("Verificando User : " + userListItem.getName());
            return ResponseEntity.status(HttpStatus.OK).body(userListItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

 }
