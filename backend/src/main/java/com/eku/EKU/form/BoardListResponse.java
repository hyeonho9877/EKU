package com.eku.EKU.form;

import com.eku.EKU.domain.InfoBoard;
import com.eku.EKU.utils.RelativeTimeConverter;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import static com.eku.EKU.utils.RelativeTimeConverter.convertToRelativeTime;

@SuperBuilder
@Data
public class BoardListResponse {
    private Long id;
    private String writer;
    private String title;
    private Long no;
    private String time;
    private Integer view;

    public BoardListResponse(InfoBoard board) {
        this.id = board.getId();
        this.writer = board.getDepartment() + " " + board.getName();
        this.title = board.getTitle();
        this.no = board.getNo().getStudNo();
        this.time = convertToRelativeTime(board.getWrittenTime());
        this.view = board.getView();
    }
}
