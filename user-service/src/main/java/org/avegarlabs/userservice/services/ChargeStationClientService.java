package org.avegarlabs.userservice.services;

import org.avegarlabs.userservice.dto.ChargeStationUseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
public class ChargeStationClientService {

    @Autowired
    private RestTemplate template;

    public List<ChargeStationUseResponse> fecthUserActivity(String userId){
        return List.of(Objects.requireNonNull(template.getForObject("http://localhost:8085/api/station/" + userId + "/charges", ChargeStationUseResponse.class)));
    }

}
