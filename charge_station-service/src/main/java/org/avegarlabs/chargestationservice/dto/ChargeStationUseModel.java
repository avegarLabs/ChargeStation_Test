package org.avegarlabs.chargestationservice.dto;

import lombok.*;
import org.avegarlabs.chargestationservice.models.ChargeStation;
import org.avegarlabs.chargestationservice.models.enums.ChargerType;
import org.avegarlabs.chargestationservice.models.enums.ChargingStationStatus;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChargeStationUseModel {
    private String userId;
    private String stationId;
    private double charge_time;
}
