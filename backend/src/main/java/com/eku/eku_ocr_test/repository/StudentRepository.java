package com.eku.eku_ocr_test.repository;

import com.eku.eku_ocr_test.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Student에 대한 Repository
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentByEmail(String email);
    Optional<Student> findStudentByNo(Long no);
}
