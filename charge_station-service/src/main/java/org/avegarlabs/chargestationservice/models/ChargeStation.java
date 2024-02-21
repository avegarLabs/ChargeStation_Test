package org.avegarlabs.chargestationservice.models;

import lombok.*;
import org.avegarlabs.chargestationservice.models.enums.ChargerType;
import org.avegarlabs.chargestationservice.models.enums.ChargingStationStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class ChargeStation {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    private String id;
    private String address;
    private double latitude;
    private double longitude;
    private ChargerType chargerType;
    private int numberOfChargingPoints;
    private ChargingStationStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy="station", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChargeStationUse> stationUseSet;
}
