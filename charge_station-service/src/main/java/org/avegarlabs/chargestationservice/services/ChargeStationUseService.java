package org.avegarlabs.chargestationservice.services;

import org.avegarlabs.chargestationservice.dto.ChargeStationListItems;
import org.avegarlabs.chargestationservice.dto.ChargeStationModel;
import org.avegarlabs.chargestationservice.dto.ChargeStationUseModel;
import org.avegarlabs.chargestationservice.models.ChargeStation;
import org.avegarlabs.chargestationservice.models.ChargeStationUse;
import org.avegarlabs.chargestationservice.models.enums.ChargingStationStatus;
import org.avegarlabs.chargestationservice.repositories.ChargeStationRepository;
import org.avegarlabs.chargestationservice.repositories.ChargeStationUseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChargeStationUseService {

    @Autowired
    ChargeStationUseRepository repository;

    @Autowired
    ChargeStationRepository stationRepository;


    public String chargeInStation(ChargeStationUseModel stationModel){
        ChargeStationUse station = mapChargeStationUseModelToChargeStationUse(stationModel);
        repository.save(station);
        return station.getId();
    }


   private ChargeStationUse mapChargeStationUseModelToChargeStationUse(ChargeStationUseModel model) {
        ChargeStation station = stationRepository.findById(model.getStationId()).get();
        return ChargeStationUse.builder()
                .userId(model.getUserId())
                .station(station)
                .charge_time(model.getCharge_time())
                .build();
    }


}
