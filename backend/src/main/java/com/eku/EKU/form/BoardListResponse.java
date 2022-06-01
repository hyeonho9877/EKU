package com.eku.EKU.form;

import com.eku.EKU.domain.FreeBoard;
import com.eku.EKU.domain.InfoBoard;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

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
    private List<FileSystemResource> images;

    public BoardListResponse(InfoBoard board) {
        this.id = board.getId();
        this.writer = board.getDepartment() + " " + board.getName();
        this.title = board.getTitle();
        this.no = board.getNo().getStudNo();
        this.time = convertToRelativeTime(board.getWrittenTime());
        this.view = board.getView();
    }

    public BoardListResponse(FreeBoard board) {
        this.id = board.getId();
        this.writer = board.getStudent().getStudNo() + " " + board.getStudent().getName();
        this.title = board.getTitle();
        this.no = board.getStudent().getStudNo();
        this.time = convertToRelativeTime(board.getTime());
        this.view = board.getView();
    }
}
