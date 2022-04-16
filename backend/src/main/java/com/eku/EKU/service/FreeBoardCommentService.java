package com.eku.EKU.service;

import com.eku.EKU.domain.FreeBoard;
import com.eku.EKU.domain.FreeBoardComment;
import com.eku.EKU.domain.FreeBoardCommentResponse;
import com.eku.EKU.domain.Student;
import com.eku.EKU.exceptions.NoSuchStudentException;
import com.eku.EKU.form.CommentForm;
import com.eku.EKU.repository.FreeBoardCommentRepository;
import com.eku.EKU.repository.FreeBoardRepository;
import com.eku.EKU.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * 자유 게시판 댓글 작성의 로직을 구현하는 Service 클래스
 */
@Service
public class FreeBoardCommentService {

    private final FreeBoardCommentRepository freeBoardCommentRepository;
    private final StudentRepository studentRepository;
    private final FreeBoardRepository freeBoardRepository;

    public FreeBoardCommentService(FreeBoardCommentRepository freeBoardCommentRepository, StudentRepository studentRepository, FreeBoardRepository freeBoardRepository) {
        this.freeBoardCommentRepository = freeBoardCommentRepository;
        this.studentRepository = studentRepository;
        this.freeBoardRepository = freeBoardRepository;
    }

    /**
     * FreeBoardComment 객체를 생성하고 해당 객체를 db에 저장하는 메소드
     * @param form 작성하려는 댓글의 정보를 저장하고 있는 Form 객체
     * @return
     * @throws NoSuchStudentException
     * @throws IllegalStateException
     */
    public FreeBoardCommentResponse writeComment(CommentForm form) throws NoSuchStudentException, IllegalStateException {
        Student writer = studentRepository.findById(form.getWriter())
                .orElseThrow(NoSuchStudentException::new);
        FreeBoard originalArticle = freeBoardRepository.findById(form.getArticleID()).orElseThrow();
        FreeBoardComment comment = FreeBoardComment.builder()
                .content((form.getContent()))
                .writtenTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .writer(writer)
                .original(originalArticle)
                .build();
        return new FreeBoardCommentResponse(freeBoardCommentRepository.save(comment));
    }

    /**
     * 자유게시판댓글 db에서 요청한 댓글 삭제
     * @param form 삭제할 댓글의 정보가 들어있는 Form 객체
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    public void deleteComment(CommentForm form) throws IllegalArgumentException, NoSuchElementException {
        freeBoardCommentRepository.deleteById(form.getCommentId());
    }

    /**
     * 자유게시판댓글 db에서 요청한 댓글 수정
     * @param form 수정할 댓글의 정보가 들어있는 Form 객체
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    @Transactional
    public void updateComment(CommentForm form) throws IllegalArgumentException, NoSuchElementException {
        FreeBoardComment target = freeBoardCommentRepository.findById(form.getCommentId()).orElseThrow();
        if (form.getContent() != null) {
            target.setContent(form.getContent());
        }
    }
}
