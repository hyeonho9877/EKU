package com.eku.EKU.repository;

import com.eku.EKU.domain.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College, String> {
}