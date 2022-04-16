package com.eku.EKU.domain;

import com.eku.EKU.form.CommentForm;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public FreeBoardCommentResponse(CommentForm form) {
        this.fId = 0;
        this.content = form.getContent();
        this.writtenTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.writer = form.getWriter();
    }
}
