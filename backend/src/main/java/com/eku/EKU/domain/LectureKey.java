package com.eku.EKU.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LectureKey implements Serializable {
    private Long lectureId;
    private LecturePrototypeKey prototypeKey;
}
