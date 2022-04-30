package com.eku.EKU.repository;

import com.eku.EKU.domain.Schedule;
import com.eku.EKU.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Schedule> {
    Optional<Schedule> findAllByStudNoAndPassword(Student student, long password);
}
