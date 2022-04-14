package com.eku.EKU.controller;

import com.eku.EKU.exceptions.NoSuchArticleException;
import com.eku.EKU.exceptions.NoSuchStudentException;
import com.eku.EKU.form.CommentForm;
import com.eku.EKU.service.FreeBoardCommentService;
import com.eku.EKU.service.InfoBoardCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

/**
 * 댓글 작성 요청을 담당하는 컨트롤러
 */
@RestController
public class CommentController {

    private final FreeBoardCommentService freeBoardCommentService;
    private final InfoBoardCommentService infoBoardCommentService;

    public CommentController(FreeBoardCommentService freeBoardCommentService, InfoBoardCommentService infoBoardCommentService) {
        this.freeBoardCommentService = freeBoardCommentService;
        this.infoBoardCommentService = infoBoardCommentService;
    }

    /**
     * 자유 게시판 댓글 작성
     * @param form 작성하려는 댓글의 정보를 담고 있는 Form 객체
     * @return 성공적으로 작성되면 HTTP.OK, 아니면 경우에 따라 BADREQUEST 혹은 INTERNAL SERVER ERROR 반환
     */
    @PostMapping("/comment/free/write")
    public ResponseEntity<?> writeFreeComment(@RequestBody CommentForm form) {
        try {
            return ResponseEntity.ok(freeBoardCommentService.writeComment(form));
        } catch (NoSuchStudentException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * 자유 게시판 댓글 삭제 메소드
     * @param form 삭제하려는 댓글의 정보가 담긴 Form 객체
     * @return 성공 -> ok와 함께 삭제한 댓글의 id, 실패 -> internal server error와 함께 삭제에 실패한 댓글의 id
     */
    @PostMapping("/comment/free/delete")
    public ResponseEntity<?> deleteFreeComment(@RequestBody CommentForm form) {
        System.out.println(form);
        try {
            freeBoardCommentService.deleteComment(form);
            return ResponseEntity.ok(form.getCommentId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(form.getCommentId());
        }
    }

    /**
     * 자유 게시판 댓글 수정 메소드
     * @param form 수정하려는 댓글의 정보가 담긴 Form 객체
     * @return 성공 -> ok와 함께 수정한 댓글의 id, 실패 -> bad request와 함께 수정에 실패한 댓글의 id
     */
    @PostMapping("/comment/free/update")
    public ResponseEntity<?> updateFreeComment(@RequestBody CommentForm form) {
        try {
            freeBoardCommentService.updateComment(form);
            return ResponseEntity.ok(form.getCommentId());
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(form.getCommentId());
        }
    }

    @PostMapping("/comment/info/write")
    public ResponseEntity<?> writeInfoComment(@RequestBody CommentForm form) {
        try {
            return ResponseEntity.ok(infoBoardCommentService.applyComment(form));
        } catch (NoSuchStudentException | NoSuchArticleException exception) {
            return ResponseEntity.internalServerError().body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/comment/info/delete")
    public ResponseEntity<?> deleteInfoComment(@RequestBody CommentForm form) {
        try {
            infoBoardCommentService.deleteComment(form);
            return ResponseEntity.ok(form.getCommentId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(form.getCommentId());
        }
    }

    @PostMapping("/comment/info/update")
    public ResponseEntity<?> updateInfoComment(@RequestBody CommentForm form) {
        try {
            infoBoardCommentService.updateComment(form);
            return ResponseEntity.ok(form.getCommentId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(form.getCommentId());
        }
    }
}
