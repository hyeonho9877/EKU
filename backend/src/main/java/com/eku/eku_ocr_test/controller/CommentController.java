package com.eku.eku_ocr_test.controller;

import com.eku.eku_ocr_test.exceptions.NoSuchStudentException;
import com.eku.eku_ocr_test.form.CommentForm;
import com.eku.eku_ocr_test.service.FreeBoardCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 작성 요청을 담당하는 컨트롤러
 */
@RestController
public class CommentController {

    private final FreeBoardCommentService freeBoardCommentService;

    public CommentController(FreeBoardCommentService freeBoardCommentService) {
        this.freeBoardCommentService = freeBoardCommentService;
    }

    /**
     * 자유 게시판 댓글 작성
     * @param form 작성하려는 댓글의 정보를 담고 있는 Form 객체
     * @return 성공적으로 작성되면 HTTP.OK, 아니면 경우에 따라 BADREQUEST 혹은 INTERNAL SERVER ERROR 반환
     */
    @PostMapping("/comment/free")
    public ResponseEntity<?> writeComment(CommentForm form) {
        try {
            return ResponseEntity.ok(freeBoardCommentService.writeComment(form).get());
        } catch (NoSuchStudentException exception) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
