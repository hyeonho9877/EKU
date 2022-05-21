package com.eku.EKU.form;


import lombok.Data;


@Data
public class InfoBoardForm {
    private Long id;
    private Long writerNo;
    private String name;
    private String department;
    private String title;
    private String content;
    private String time;
    private String building;
    private int lectureBuilding;
    private String keyword;
}
