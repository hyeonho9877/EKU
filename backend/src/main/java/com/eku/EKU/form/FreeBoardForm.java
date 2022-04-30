package com.eku.EKU.form;

import lombok.Data;



@Data
public class FreeBoardForm {
    private long id;
    private long writerNo;
    private String department;
    private String title;
    private String content;
    private int view;
    private String time;
}
