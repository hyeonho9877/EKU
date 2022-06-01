package com.eku.EKU.repository;

import com.eku.EKU.domain.Doodle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoodleRepository extends JpaRepository<Doodle, Long> {
    List<Doodle> findByBuilding_BeaconIdIsOrderByDoodleIdDesc(String beaconId, Pageable pageable);

}
