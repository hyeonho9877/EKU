package com.eku.EKU.repository;

import com.eku.EKU.domain.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * FreeBoard에 대한 Repository
 */
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    Optional<FreeBoard> findFreeBoardById(Long Id);
}
