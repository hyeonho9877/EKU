package com.eku.EKU.repository;


import com.eku.EKU.domain.Image;
import com.eku.EKU.domain.InfoBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByInfoBoard(InfoBoard infoBoard);
    List<Image> findAllByInfoBoard_Id(@NonNull long id);
}
