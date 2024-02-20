package org.avegarlabs.chargestationservice.controllers;

import lombok.RequiredArgsConstructor;
import org.avegarlabs.chargestationservice.dto.ChargeStationListItems;
import org.avegarlabs.chargestationservice.dto.ChargeStationModel;
import org.avegarlabs.chargestationservice.services.ChargeStationService;
import org.avegarlabs.chargestationservice.util.ErrorMessage;
import org.avegarlabs.chargestationservice.util.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/station")
@CrossOrigin
public class ChargeStationController {

    @Autowired
    ChargeStationService service;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeStation(@PathVariable String id) {
        try {
            service.deleteStation(id);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Successfully completed the operation."));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @GetMapping("/change/{id}")
    public ResponseEntity<Object> changeStatus(@PathVariable String id) {
        try {
            ChargeStationListItems item = service.updateStationState(id);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }



}

