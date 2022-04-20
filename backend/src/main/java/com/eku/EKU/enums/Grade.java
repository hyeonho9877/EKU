package com.eku.EKU.enums;

public enum Grade {
    AP(4.5),
    A(4.0),
    BP(3.5),
    B(3.0),
    CP(2.5),
    C(2.0),
    DP(1.5),
    D(1.0),
    F(0.0);

    Grade(double v) {
        this.score=v;
    }

    public double getScore() {
        return this.score;
    }
    final private double score;
}
