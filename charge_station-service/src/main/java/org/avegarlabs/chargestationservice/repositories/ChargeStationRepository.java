package org.avegarlabs.chargestationservice.repositories;

import org.avegarlabs.chargestationservice.models.ChargeStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeStationRepository extends JpaRepository<ChargeStation, String> {
}
