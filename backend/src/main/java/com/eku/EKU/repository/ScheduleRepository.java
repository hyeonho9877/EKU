package com.eku.EKU.repository;

import com.eku.EKU.domain.Schedule;
import com.eku.EKU.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByStudentAndPassword(Student student, long password);
}
