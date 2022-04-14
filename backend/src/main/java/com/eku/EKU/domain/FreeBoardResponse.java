package com.eku.EKU.domain;

import lombok.Data;

@Data
public class FreeBoardResponse {
    private Long id;
    private Long studNo;
    private String department;
    private String title;
    private String content;
    private int view;
    private String time;

    public FreeBoardResponse(FreeBoard board) {
        this.id= board.getId();
        this.studNo = board.getStudent().getStudNo();
        this.department = board.getDepartment();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.time = board.getTime();
        this.view = board.getView();
    }
}
