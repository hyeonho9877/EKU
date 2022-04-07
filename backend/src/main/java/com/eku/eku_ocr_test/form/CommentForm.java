package com.eku.eku_ocr_test.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 댓글 작성 요청에 사용되는 Form 객체
 */
@Data
public class CommentForm {
    private long fId;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime time;
    private String writer;
    private long articleID;
}
