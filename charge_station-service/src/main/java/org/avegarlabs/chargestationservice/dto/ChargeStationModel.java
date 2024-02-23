package org.avegarlabs.chargestationservice.dto;

import lombok.*;
import org.avegarlabs.chargestationservice.models.enums.ChargerType;
import org.avegarlabs.chargestationservice.models.enums.ChargingStationStatus;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChargeStationModel {
    private String description;
    private String address;
    private double latitude;
    private double longitude;
    private ChargerType chargerType;
    private int numberOfChargingPoints;
    private ChargingStationStatus status;
}
