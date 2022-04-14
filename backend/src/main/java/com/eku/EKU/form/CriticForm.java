package com.eku.EKU.form;

import com.eku.EKU.domain.Grade;
import lombok.Data;

@Data
public class CriticForm {
    private long criticId;
    private String email;
    private String content;
    private Grade grade;
    private String profName;
    private String lectureName;
    private float star;
}
