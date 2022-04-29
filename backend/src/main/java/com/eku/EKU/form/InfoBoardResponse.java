package com.eku.EKU.form;

import com.eku.EKU.domain.InfoBoard;
import lombok.Data;

import java.util.List;

@Data
public class InfoBoardResponse {
    private long id;
    private long writerNo;
    private String department;
    private String title;
    private String content;
    private String time;
    private String building;
    private String name;
    private List<InfoBoardCommentResponse> commentList;

    public InfoBoardResponse(InfoBoard board) {
        this.id= board.getId();
        this.writerNo = board.getNo().getStudNo();
        this.department = board.getDepartment();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.time = board.getWrittenTime();
        this.building = board.getBuilding();
        this.name = board.getName();
    }
}
