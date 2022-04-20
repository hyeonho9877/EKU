package com.eku.EKU.repository;

import com.eku.EKU.domain.LecturePrototype;
import com.eku.EKU.domain.LecturePrototypeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LecturePrototypeRepository extends JpaRepository<LecturePrototype, LecturePrototypeKey> {
    List<LecturePrototype> findByLectureNameContainsOrProfessorContains(String lectureName, String professor);

}