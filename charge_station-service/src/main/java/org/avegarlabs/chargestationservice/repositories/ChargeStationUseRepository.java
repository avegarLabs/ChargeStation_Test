package org.avegarlabs.chargestationservice.repositories;

import org.avegarlabs.chargestationservice.models.ChargeStationUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeStationUseRepository extends JpaRepository<ChargeStationUse, String> {
    List<ChargeStationUse> findAllByUserId(String id);
}
