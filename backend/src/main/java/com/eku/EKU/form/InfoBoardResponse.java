package com.eku.EKU.form;

import com.eku.EKU.domain.InfoBoard;
import lombok.Data;

import java.util.List;

import static com.eku.EKU.utils.RelativeTimeConverter.convertToRelativeTime;

@Data
public class InfoBoardResponse {
    private long id;
    private long writerNo;
    private String writer;
    private String title;
    private String content;
    private String time;
    private String building;
    private Integer view;
    private List<String> imageList;
    private List<InfoBoardCommentResponse> commentList;

    public InfoBoardResponse(InfoBoard board) {
        this.id= board.getId();
        this.writerNo = board.getNo().getStudNo();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.time = convertToRelativeTime(board.getWrittenTime());
        this.building = board.getBuilding();
        this.writer = board.getDepartment() + " " + board.getName();
        this.view = board.getView();
    }
}
