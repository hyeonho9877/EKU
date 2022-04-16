package com.eku.EKU.domain;

import lombok.Data;

@Data
public class InfoBoardResponse {
    private Long id;
    private Long studNo;
    private String department;
    private String title;
    private String content;
    private String time;

    public InfoBoardResponse(InfoBoard board) {
        this.id= board.getId();
        this.studNo = board.getWriter().getStudNo();
        this.department = board.getDepartment();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.time = board.getWrittenTime();

    }
}
