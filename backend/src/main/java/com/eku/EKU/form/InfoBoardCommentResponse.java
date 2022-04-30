package com.eku.EKU.form;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class InfoBoardCommentResponse {
    private long iId;
    private String content;
    private String writtenTime;
    private long writer;

    public InfoBoardCommentResponse(CommentForm form) {
        this.iId = 0;
        this.content = form.getContent();
        this.writtenTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.writer = form.getWriter();
    }
    public InfoBoardCommentResponse(){}
}
