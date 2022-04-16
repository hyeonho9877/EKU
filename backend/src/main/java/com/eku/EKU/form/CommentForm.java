package com.eku.EKU.form;

import lombok.Data;

/**
 * 댓글 작성 요청에 사용되는 Form 객체
 */
@Data
public class CommentForm {
    private long commentId;
    private String content;
    private long writer;
    private long articleID;
}
