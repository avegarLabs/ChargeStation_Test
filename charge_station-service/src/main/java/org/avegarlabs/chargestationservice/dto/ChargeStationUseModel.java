package org.avegarlabs.chargestationservice.dto;

import lombok.*;

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
