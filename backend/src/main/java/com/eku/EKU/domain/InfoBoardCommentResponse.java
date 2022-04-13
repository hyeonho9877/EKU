package com.eku.EKU.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InfoBoardCommentResponse {
    private long iId;
    private String content;
    private LocalDateTime writtenTime;
    private long writer;

    public InfoBoardCommentResponse(InfoBoardComment comment) {
        this.iId = comment.getIcId();
        this.content = comment.getContent();
        this.writtenTime = comment.getWrittenTime();
        this.writer = comment.getWriter().getStudNo();
    }
}
