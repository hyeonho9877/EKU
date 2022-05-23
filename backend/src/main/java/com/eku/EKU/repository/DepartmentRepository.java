package com.eku.EKU.repository;

import com.eku.EKU.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select d from Department as d group by d.deptName order by d.deptName")
    List<Department> findAllByDistinctDeptNameOrderByDeptName();
}