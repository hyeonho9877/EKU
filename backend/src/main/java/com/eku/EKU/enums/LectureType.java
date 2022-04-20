package com.eku.EKU.enums;

public enum LectureType {
    GE("GE"), Major("Major");

    LectureType(String type) {
        this.type=type;
    }

    public String getType() {
        return type;
    }

    final private String type;
}
