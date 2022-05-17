package com.eku.EKU.repository;

import com.eku.EKU.domain.InfoBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface InfoBoardRepository extends JpaRepository<InfoBoard, Long> {

    @Query(value = "SELECT i FROM InfoBoard AS i where substring(i.building, :building, 1) = '1' order by i.writtenTime desc ")
    Page<InfoBoard> findAllByBuildingOrderByWrittenTime(@Param("building") int building, Pageable pageable);

    List<InfoBoard> findByIdIsLessThanOrderByWrittenTimeDesc(@NonNull Long id, Pageable pageable);
    List<InfoBoard> findByIdIsGreaterThanOrderByWrittenTimeDesc(@NonNull Long id);
}
