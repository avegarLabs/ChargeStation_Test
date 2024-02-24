package org.avegarlabs.chargestationservice.services;

import lombok.extern.slf4j.Slf4j;
import org.avegarlabs.chargestationservice.dto.ChargeStationListItems;
import org.avegarlabs.chargestationservice.dto.ChargeStationModel;
import org.avegarlabs.chargestationservice.models.ChargeStation;
import org.avegarlabs.chargestationservice.models.enums.ChargingStationStatus;
import org.avegarlabs.chargestationservice.repositories.ChargeStationRepository;
import org.avegarlabs.chargestationservice.util.CreateMoniker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChargeStationService {

    @Autowired
    ChargeStationRepository repository;

    @Autowired
    CreateMoniker moniker;


    public List<ChargeStationListItems> allStations() {
        List<ChargeStation> list = repository.findAll();
        return list.stream().map(this::mapChargeStationToChargeStationListItems).collect(Collectors.toList());
    }

    public ChargeStationListItems persistStation(ChargeStationModel stationModel) {
        ChargeStation station = mapChargeStationModelToChargeStation(stationModel);
        repository.save(station);
        return mapChargeStationToChargeStationListItems(station);
    }

    public ChargeStationListItems updateStation(String id, ChargeStationModel stationModel) {
        ChargeStation station = updateStationData(id, stationModel);
        repository.save(station);
        return mapChargeStationToChargeStationListItems(station);
    }

    public void deleteStation(String id) {
        repository.deleteById(id);
    }

    public ChargeStationListItems updateStationState(String id) {
        ChargeStation station = repository.findById(id).get();
        if (station.getStatus().equals(ChargingStationStatus.AVAILABLE)) {
            station.setStatus(ChargingStationStatus.IN_USE);
        } else {
            station.setStatus(ChargingStationStatus.AVAILABLE);
        }
        repository.save(station);
        return mapChargeStationToChargeStationListItems(station);
    }

    public String getChargeStationStatus(String id) {
        Optional<ChargeStation> chargeStation = repository.findById(id);
        if (chargeStation.isEmpty()) {
            throw new RuntimeException("Station with id: " + id + "not found");
        } else {
            return chargeStation.get().getStatus().getDisplayName();
        }
    }

    public ChargeStationListItems getByMoniker(String moniker){
        Optional<ChargeStation> chargeStation = repository.findByMoniker(moniker);
        if (chargeStation.isEmpty())
            throw new RuntimeException("Station with moniker: " + moniker + "not found");
        return mapChargeStationToChargeStationListItems(chargeStation.get());
    }


    public ChargeStationListItems mapChargeStationToChargeStationListItems(ChargeStation station) {
        return ChargeStationListItems.builder()
                .id(station.getId())
                .description(station.getDescription())
                .address(station.getAddress())
                .longitude(station.getLongitude())
                .latitude(station.getLatitude())
                .chargerType(station.getChargerType().getDisplayName())
                .numberOfChargingPoints(station.getNumberOfChargingPoints())
                .status(station.getStatus().getDisplayName())
                .moniker(station.getMoniker())
                .build();
    }

    private ChargeStation mapChargeStationModelToChargeStation(ChargeStationModel model) {
        return ChargeStation.builder()
                .description(model.getDescription())
                .address(model.getAddress())
                .latitude(model.getLatitude())
                .longitude(model.getLongitude())
                .numberOfChargingPoints(model.getNumberOfChargingPoints())
                .status(model.getStatus())
                .chargerType(model.getChargerType())
                .moniker(moniker.createMoniker(model.getDescription()))
                .build();
    }

    private ChargeStation updateStationData(String stationId, ChargeStationModel model) {
        ChargeStation station = repository.findById(stationId).get();
        station.setDescription(model.getDescription());
        station.setAddress(model.getAddress());
        station.setLongitude(model.getLongitude());
        station.setLatitude(model.getLatitude());
        station.setChargerType(model.getChargerType());
        station.setNumberOfChargingPoints(model.getNumberOfChargingPoints());
        station.setStatus(model.getStatus());
        return station;
    }


}
