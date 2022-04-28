package com.eku.EKU;

import com.eku.EKU.enums.LectureType;
import org.junit.jupiter.api.Test;

public class RegularTest {

    @Test
    void enumTest(){
        System.out.println(LectureType.GE.getType());
    }
}
