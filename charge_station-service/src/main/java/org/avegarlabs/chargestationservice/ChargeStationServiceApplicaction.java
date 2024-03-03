package org.avegarlabs.chargestationservice;

import org.avegarlabs.chargestationservice.models.ChargeStation;
import org.avegarlabs.chargestationservice.models.enums.ChargerType;
import org.avegarlabs.chargestationservice.models.enums.ChargingStationStatus;
import org.avegarlabs.chargestationservice.repositories.ChargeStationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class ChargeStationServiceApplicaction {

    public static void main(String[] args) {
        SpringApplication.run(ChargeStationServiceApplicaction.class, args);
    }

    @Bean
    public CommandLineRunner loadData(ChargeStationRepository repository) {
        return args -> {
            List<ChargeStation> stataions = repository.findAll();
            if (stataions.size() == 0) {
                ChargeStation station = new ChargeStation();
                station.setDescription("Abb Station");
                station.setMoniker("abb-station");
                station.setAddress("Abb Station some address");
                station.setLatitude(129.45);
                station.setLongitude(421.45);
                station.setNumberOfChargingPoints(4);
                station.setChargerType(ChargerType.AC);
                station.setStatus(ChargingStationStatus.AVAILABLE);
                repository.save(station);

                ChargeStation station1 = new ChargeStation();
                station1.setDescription("Blink Station");
                station.setMoniker("blink-station");
                station1.setAddress("Blink Station some address");
                station1.setLatitude(123.45);
                station1.setLongitude(321.45);
                station1.setNumberOfChargingPoints(4);
                station1.setChargerType(ChargerType.DC);
                station1.setStatus(ChargingStationStatus.AVAILABLE);
                repository.save(station1);

                ChargeStation station2 = new ChargeStation();
                station2.setDescription("Electrify America Station");
                station2.setMoniker("electrify-america-station");
                station2.setAddress("Electrify America some address");
                station2.setLatitude(153.45);
                station2.setLongitude(721.45);
                station2.setNumberOfChargingPoints(4);
                station2.setChargerType(ChargerType.AC);
                station2.setStatus(ChargingStationStatus.AVAILABLE);
                repository.save(station2);


                ChargeStation station3 = new ChargeStation();
                station3.setDescription("Evbox Station");
                station3.setDescription("evbox-station");
                station3.setAddress("Evbox Station some address");
                station3.setLatitude(183.45);
                station3.setLongitude(121.45);
                station3.setNumberOfChargingPoints(2);
                station3.setChargerType(ChargerType.AC);
                station3.setStatus(ChargingStationStatus.IN_USE);
                repository.save(station3);

            }
        };
    }
}
