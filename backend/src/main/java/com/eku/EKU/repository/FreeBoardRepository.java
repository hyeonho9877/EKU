package com.eku.EKU.repository;

import com.eku.EKU.domain.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * FreeBoard에 대한 Repository
 */
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
}
