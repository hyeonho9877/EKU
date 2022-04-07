package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.FreeBoardComment;
import com.eku.eku_ocr_test.domain.Student;
import com.eku.eku_ocr_test.exceptions.NoSuchStudentException;
import com.eku.eku_ocr_test.form.CommentForm;
import com.eku.eku_ocr_test.repository.FreeBoardCommentRepository;
import com.eku.eku_ocr_test.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 자유 게시판 댓글 작성의 로직을 구현하는 Service 클래스
 */
@Service
public class FreeBoardCommentService {

    private final FreeBoardCommentRepository freeBoardCommentRepository;
    private final StudentRepository studentRepository;

    public FreeBoardCommentService(FreeBoardCommentRepository freeBoardCommentRepository, StudentRepository studentRepository) {
        this.freeBoardCommentRepository = freeBoardCommentRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * FreeBoardComment 객체를 생성하고 해당 객체를 db에 저장하는 메소드
     * @param form 작성하려는 댓글의 정보를 저장하고 있는 Form 객체
     * @return
     * @throws NoSuchStudentException
     * @throws IllegalStateException
     */
    public Optional<FreeBoardComment> writeComment(CommentForm form) throws NoSuchStudentException, IllegalStateException{
        Student writer = studentRepository.findStudentByEmail(form.getWriter())
                .orElseThrow(NoSuchStudentException::new);
        FreeBoardComment comment = FreeBoardComment.builder()
                .writer(writer)
                .title(form.getTitle())
                .content((form.getTitle()))
                .view(0)
                .time(form.getTime())
                .build();
        return Optional.of(freeBoardCommentRepository.save(comment));
    }
}
