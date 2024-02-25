package org.avegarlabs.chargestationservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avegarlabs.chargestationservice.dto.ChargeStationListItems;
import org.avegarlabs.chargestationservice.dto.ChargeStationModel;
import org.avegarlabs.chargestationservice.dto.ChargeStationUseModel;
import org.avegarlabs.chargestationservice.dto.ChargeStationUseResponse;
import org.avegarlabs.chargestationservice.services.ChargeStationService;
import org.avegarlabs.chargestationservice.services.ChargeStationUseService;
import org.avegarlabs.chargestationservice.util.ErrorMessage;
import org.avegarlabs.chargestationservice.util.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
@CrossOrigin
@Slf4j
public class ChargeStationPublicController {

    @Autowired
    ChargeStationService service;

    @Autowired
    ChargeStationUseService useService;

    @GetMapping
    public ResponseEntity<Object> getStations() {
        try {
            List<ChargeStationListItems> listItems = service.allStations();
            return ResponseEntity.status(HttpStatus.OK).body(listItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    }

