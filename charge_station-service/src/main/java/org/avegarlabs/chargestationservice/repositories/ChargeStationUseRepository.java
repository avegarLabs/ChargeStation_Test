package org.avegarlabs.chargestationservice.repositories;

import org.avegarlabs.chargestationservice.models.ChargeStationUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeStationUseRepository extends JpaRepository<ChargeStationUse, String> {
}
