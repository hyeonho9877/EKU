package com.eku.EKU.repository;

import com.eku.EKU.domain.InfoBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface InfoBoardRepository extends JpaRepository<InfoBoard, Long> {

    @Query(value = "SELECT i FROM InfoBoard AS i where substring(i.building, :building, 1) = '1'")
    Page<InfoBoard> findAllByBuilding(@Param("building") int building, Pageable pageable);

}
