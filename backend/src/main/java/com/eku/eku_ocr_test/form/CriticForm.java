package com.eku.eku_ocr_test.form;

import com.eku.eku_ocr_test.domain.Grade;
import lombok.Data;

@Data
public class CriticForm {
    private long criticId;
    private String email;
    private String content;
    private Grade grade;
    private String profName;
    private String lectureName;
}
