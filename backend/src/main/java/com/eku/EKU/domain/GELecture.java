package com.eku.EKU.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class GELecture extends Lecture {

    @ManyToOne
    private LectureSubGroup lectureGroup;
}
