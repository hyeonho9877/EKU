package com.eku.EKU.repository;

import com.eku.EKU.domain.MappingKey;
import com.eku.EKU.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * MappingKey에 대한 Repository
 */
public interface MappingKeyRepository extends JpaRepository<MappingKey, String> {
    Optional<MappingKey> findMappingKeyByStudent(Student student);
}
