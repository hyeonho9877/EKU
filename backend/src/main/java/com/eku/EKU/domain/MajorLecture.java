package com.eku.EKU.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MajorLecture extends Lecture{
    @Column(name = "lecture_dept", nullable = false)
    private String dept;
}
