package com.eku.EKU.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GELecture extends Lecture {
    @Column(name = "lecture_group", nullable = false)
    private String lectureGroup;
}
