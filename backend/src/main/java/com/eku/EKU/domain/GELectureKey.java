package com.eku.EKU.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class GELectureKey implements Serializable {
    private int year;
    private String lectureNo;
    private int semester;
}
