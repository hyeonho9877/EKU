package com.eku.EKU.domain;

import lombok.Data;

@Data
public class FreeBoardCommentResponse {
    private long fId;
    private String content;
    private String writtenTime;
    private long writer;

    public FreeBoardCommentResponse(FreeBoardComment comment) {
        this.fId = comment.getFId();
        this.content = comment.getContent();
        this.writtenTime = comment.getWrittenTime();
        this.writer = comment.getWriter().getStudNo();
    }
}
