package com.eku.EKU.repository;

import com.eku.EKU.domain.Doodle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoodleRepository extends JpaRepository<Doodle, Long> {
    Page<Doodle> findAllByBuildingBeaconIdEquals(Pageable page, String beaconId);
}
