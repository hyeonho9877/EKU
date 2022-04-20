package com.eku.EKU.domain;

import com.eku.EKU.enums.LectureType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@IdClass(LecturePrototypeKey.class)
public class LecturePrototype {
    @Id
    private String lectureName;
    @Id
    private String professor;

    @Column(nullable = false)
    private LectureType lectureType;
    @Column(nullable = false)
    private Float star;


}
