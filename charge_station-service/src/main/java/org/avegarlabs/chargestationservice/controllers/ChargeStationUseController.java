package org.avegarlabs.chargestationservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avegarlabs.chargestationservice.dto.ChargeStationUseModel;
import org.avegarlabs.chargestationservice.services.ChargeStationUseService;
import org.avegarlabs.chargestationservice.util.ErrorMessage;
import org.avegarlabs.chargestationservice.util.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stationuse")
@CrossOrigin
@Slf4j
public class ChargeStationUseController {

    @Autowired
    ChargeStationUseService service;

    @PostMapping
    public ResponseEntity<Object> charge(@RequestBody ChargeStationUseModel model) {
        try {
            String item = service.chargeInStation(model);
            log.info("Success", item);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Successfully completed the operation."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }





}

