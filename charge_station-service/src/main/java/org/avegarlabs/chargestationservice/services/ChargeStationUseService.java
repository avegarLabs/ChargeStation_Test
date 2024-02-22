package org.avegarlabs.chargestationservice.services;

import org.avegarlabs.chargestationservice.dto.ChargeStationListItems;
import org.avegarlabs.chargestationservice.dto.ChargeStationUseModel;
import org.avegarlabs.chargestationservice.dto.ChargeStationUseResponse;
import org.avegarlabs.chargestationservice.models.ChargeStation;
import org.avegarlabs.chargestationservice.models.ChargeStationUse;
import org.avegarlabs.chargestationservice.repositories.ChargeStationRepository;
import org.avegarlabs.chargestationservice.repositories.ChargeStationUseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargeStationUseService {

    @Autowired
    ChargeStationUseRepository repository;

    @Autowired
    ChargeStationRepository stationRepository;

    @Autowired
    ChargeStationService stationService;


    public String chargeInStation(ChargeStationUseModel stationModel){
        ChargeStationUse station = mapChargeStationUseModelToChargeStationUse(stationModel);
        repository.save(station);
        return station.getId();
    }

    public List<ChargeStationUseResponse> getUserActivity(String userId){
        List<ChargeStationUse> stationUseList = repository.findAllByUserId(userId);
        return stationUseList.stream().map(this::mapChargeStationUseToChargeStationUseResponse).toList();
    }


   private ChargeStationUse mapChargeStationUseModelToChargeStationUse(ChargeStationUseModel model) {
        ChargeStation station = stationRepository.findById(model.getStationId()).get();
        return ChargeStationUse.builder()
                .userId(model.getUserId())
                .station(station)
                .charge_time(model.getCharge_time())
                .build();
    }

    private ChargeStationUseResponse mapChargeStationUseToChargeStationUseResponse(ChargeStationUse chargeStationUse){
        ChargeStationListItems listItems = stationService.mapChargeStationToChargeStationListItems(chargeStationUse.getStation());
        return ChargeStationUseResponse.builder()
                .chargeStation(listItems)
                .charge_time(chargeStationUse.getCharge_time())
                .date(chargeStationUse.getCreatedAt())
                .build();
    }


}
