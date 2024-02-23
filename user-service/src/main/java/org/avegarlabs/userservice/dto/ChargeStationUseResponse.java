package org.avegarlabs.userservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChargeStationUseResponse {
    private ChargeStationListItems chargeStation;
    private double charge_time;
    private LocalDateTime date;
}
