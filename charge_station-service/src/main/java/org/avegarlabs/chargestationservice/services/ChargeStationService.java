package org.avegarlabs.chargestationservice.services;

import org.avegarlabs.chargestationservice.dto.ChargeStationListItems;
import org.avegarlabs.chargestationservice.dto.ChargeStationModel;
import org.avegarlabs.chargestationservice.models.ChargeStation;
import org.avegarlabs.chargestationservice.models.enums.ChargingStationStatus;
import org.avegarlabs.chargestationservice.repositories.ChargeStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChargeStationService {

    @Autowired
    ChargeStationRepository repository;


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


    public ChargeStationListItems mapChargeStationToChargeStationListItems(ChargeStation station) {
        return ChargeStationListItems.builder()
                .id(station.getId())
                .address(station.getAddress())
                .longitude(station.getLongitude())
                .latitude(station.getLatitude())
                .chargerType(station.getChargerType().getDisplayName())
                .numberOfChargingPoints(station.getNumberOfChargingPoints())
                .createdAt(station.getCreatedAt())
                .updatedAt(station.getUpdatedAt())
                .status(station.getStatus().getDisplayName())
                .build();
    }

    private ChargeStation mapChargeStationModelToChargeStation(ChargeStationModel model) {
        return ChargeStation.builder()
                .address(model.getAddress())
                .latitude(model.getLatitude())
                .longitude(model.getLongitude())
                .numberOfChargingPoints(model.getNumberOfChargingPoints())
                .status(model.getStatus())
                .chargerType(model.getChargerType())
                .build();
    }

    private ChargeStation updateStationData(String stationId, ChargeStationModel model) {
        ChargeStation station = repository.findById(stationId).get();
        station.setAddress(model.getAddress());
        station.setLongitude(model.getLongitude());
        station.setLatitude(model.getLatitude());
        station.setChargerType(model.getChargerType());
        station.setNumberOfChargingPoints(model.getNumberOfChargingPoints());
        station.setStatus(model.getStatus());
        return station;
    }


}
