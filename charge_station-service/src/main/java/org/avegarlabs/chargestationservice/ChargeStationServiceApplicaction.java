package org.avegarlabs.chargestationservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
@EnableEurekaClient
public class ChargeStationServiceApplicaction {

    public static void main(String[] args) {
        SpringApplication.run(ChargeStationServiceApplicaction.class, args);
    }
}
