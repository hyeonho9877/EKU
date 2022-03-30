package com.eku.eku_ocr_test.repository;

import com.eku.eku_ocr_test.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignUpRepository extends JpaRepository<Member, Integer> {
}
