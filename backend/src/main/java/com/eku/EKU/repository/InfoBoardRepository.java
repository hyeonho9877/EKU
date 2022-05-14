package com.eku.EKU.repository;

import com.eku.EKU.domain.InfoBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InfoBoardRepository extends JpaRepository<InfoBoard, Long> {
    Optional<InfoBoard> findInfoBoardById(Long Id);
}
