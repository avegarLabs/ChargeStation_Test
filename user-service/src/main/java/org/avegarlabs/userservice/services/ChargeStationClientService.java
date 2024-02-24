package org.avegarlabs.userservice.services;

import org.avegarlabs.userservice.dto.ChargeStationUseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class ChargeStationClientService {

    @Autowired
    @LoadBalanced
    private RestTemplate template;

    public List<ChargeStationUseResponse> fecthUserActivity(String userId){
        return Collections.singletonList(template.getForObject("http://localhost:8085/api/station/" + userId + "/charges", ChargeStationUseResponse.class));
    }

}
