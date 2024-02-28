package org.avegarlabs.chargestationservice.services;

import lombok.extern.slf4j.Slf4j;
import org.avegarlabs.chargestationservice.dto.ChargeStationListItems;
import org.avegarlabs.chargestationservice.dto.ChargeStationUseModel;
import org.avegarlabs.chargestationservice.dto.ChargeStationUseResponse;
import org.avegarlabs.chargestationservice.dto.UserListItem;
import org.avegarlabs.chargestationservice.models.ChargeStation;
import org.avegarlabs.chargestationservice.models.ChargeStationUse;
import org.avegarlabs.chargestationservice.repositories.ChargeStationRepository;
import org.avegarlabs.chargestationservice.repositories.ChargeStationUseRepository;
import org.avegarlabs.chargestationservice.services.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChargeStationUseService {

    @Autowired
    ChargeStationUseRepository repository;

    @Autowired
    ChargeStationRepository stationRepository;

    @Autowired
    ChargeStationService stationService;

    @Autowired
    UserServiceClient userServiceClient;


    public ChargeStationListItems chargeInStation(ChargeStationUseModel stationModel, String token){
        ChargeStationUse station = mapChargeStationUseModelToChargeStationUse(stationModel, token);
        ChargeStationListItems item =  stationService.updateStationState(station.getStation().getId());
        repository.save(station);
        return item;
    }

    public List<ChargeStationUseResponse> getUserActivity(String userId){
        List<ChargeStationUse> stationUseList = repository.findAllByUserId(userId);
        return stationUseList.stream().map(this::mapChargeStationUseToChargeStationUseResponse).toList();
    }


   private ChargeStationUse mapChargeStationUseModelToChargeStationUse(ChargeStationUseModel model, String authorizationToken ) {
       UserListItem user = userServiceClient.fecthUserData(model.getUserId(), authorizationToken);
        ChargeStation station = stationRepository.findById(model.getStationId()).get();
        log.info(station.getDescription(), station.getStatus());
        return ChargeStationUse.builder()
                .userId(user.getId())
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
