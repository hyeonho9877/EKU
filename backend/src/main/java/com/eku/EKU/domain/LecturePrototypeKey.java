package com.eku.EKU.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LecturePrototypeKey implements Serializable {
    private String lectureName;
    private String professor;
}
