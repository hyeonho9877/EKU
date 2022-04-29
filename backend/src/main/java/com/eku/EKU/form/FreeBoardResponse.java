package com.eku.EKU.form;

import com.eku.EKU.domain.FreeBoard;
import lombok.Data;

import java.util.List;

@Data
public class FreeBoardResponse {
    private long id;
    private long writerNo;
    private String department;
    private String title;
    private String content;
    private int view;
    private String time;
    private List<FreeBoardCommentResponse> commentList;

    public FreeBoardResponse(FreeBoard board) {
        this.id= board.getId();
        this.writerNo = board.getStudent().getStudNo();
        this.department = board.getDepartment();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.time = board.getTime();
        this.view = board.getView();
    }
}
