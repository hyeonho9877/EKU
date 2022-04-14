package com.eku.EKU.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 댓글 작성 요청에 사용되는 Form 객체
 */
@Data
public class CommentForm {
    private long commentId;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime writtenTime;
    private long writer;
    private long articleID;
}
