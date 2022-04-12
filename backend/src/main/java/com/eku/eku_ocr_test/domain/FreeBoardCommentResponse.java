package com.eku.eku_ocr_test.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FreeBoardCommentResponse {
    private long fId;
    private String title;
    private String content;
    private LocalDateTime writtenTime;
    private long writer;

    public FreeBoardCommentResponse(FreeBoardComment comment) {
        this.fId = comment.getFId();
        this.title = comment.getTitle();
        this.content = comment.getContent();
        this.writtenTime = comment.getWrittenTime();
        this.writer = comment.getWriter().getStudNo();
    }
}
