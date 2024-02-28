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
@RequestMapping("/api/station")
@CrossOrigin
@Slf4j
public class ChargeStationController {

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

    @PostMapping
    public ResponseEntity<Object> saveStations(@RequestBody ChargeStationModel model) {
        try {
            ChargeStationListItems item = service.persistStation(model);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> modifiedStations(@PathVariable String id, @RequestBody ChargeStationModel model) {
        try {
            ChargeStationListItems item = service.updateStation(id, model);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }

    }

    @GetMapping("/modify/{id}")
    public ResponseEntity<Object> modifiedStatuofStations(@PathVariable String id) {
        try {
            ChargeStationListItems item = service.updateStationState(id);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeStation(@PathVariable String id) {
        try {
            service.deleteStation(id);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Successfully completed the operation."));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @PostMapping("/use")
    public ResponseEntity<Object> charge(@RequestBody ChargeStationUseModel model, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        try {
            String token = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            }
            ChargeStationListItems response = useService.chargeInStation(model, token);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Successfully charging in: " + response.getDescription()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

        @GetMapping("/status")
        public ResponseEntity<Object> showChargeStationStatus (@RequestParam("id") String id){
            try {
                String status = service.getChargeStationStatus(id);
                return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage(status));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
            }
        }

        @GetMapping("/{userId}/charges")
        public ResponseEntity<Object> showChargeStationUsesByUserId (@PathVariable String userId){
            try {
                List<ChargeStationUseResponse> listItems = useService.getUserActivity(userId);
                return ResponseEntity.status(HttpStatus.OK).body(listItems);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
            }
        }

    @GetMapping("/details/{moniker}")
     public ResponseEntity<Object> findByMoniker(@PathVariable String moniker) {
        try {
            ChargeStationListItems stationListItems = service.getByMoniker(moniker);
            return ResponseEntity.status(HttpStatus.OK).body(stationListItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }


    }

