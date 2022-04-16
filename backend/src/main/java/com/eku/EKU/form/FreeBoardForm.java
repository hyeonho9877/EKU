package com.eku.EKU.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
public class FreeBoardForm {
    private long id;
    private long studNo;
    private String department;
    private String title;
    private String content;
    private int view;
    private String time;
}
