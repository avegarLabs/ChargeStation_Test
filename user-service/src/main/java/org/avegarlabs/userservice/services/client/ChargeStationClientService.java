package org.avegarlabs.userservice.services.client;

import org.avegarlabs.userservice.dto.ChargeStationUseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ChargeStationClientService {

    @Autowired
    WebClient.Builder webClientBuilder;

    public List<ChargeStationUseResponse> fecthUserActivity(String userId){

        List<ChargeStationUseResponse> list = new ArrayList<>();
        ChargeStationUseResponse[] useList = webClientBuilder.build()
                .get()
                .uri("http://charge-station-service:8085/api/station/" + userId + "/charges")
                .retrieve()
                .bodyToMono(ChargeStationUseResponse[].class)
                .block();
        if(useList != null) {
            list = Arrays.stream(useList).toList();
        }
        return list;
    }

}
