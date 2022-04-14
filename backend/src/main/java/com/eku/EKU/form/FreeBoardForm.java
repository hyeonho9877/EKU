package com.eku.eku_ocr_test.form;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FreeBoardForm {
    private Long id;
    private Long studNo;
    private String department;
    private String title;
    private String content;
    private int view;
    private String time;
}
