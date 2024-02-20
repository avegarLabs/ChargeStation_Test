package org.avegarlabs.chargestationservice.dto;

import lombok.*;
import org.avegarlabs.chargestationservice.models.enums.ChargerType;
import org.avegarlabs.chargestationservice.models.enums.ChargingStationStatus;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChargeStationListItems {
    private String id;
    private String address;
    private double latitude;
    private double longitude;
    private String chargerType;
    private int numberOfChargingPoints;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
