package com.eku.EKU.service;

import com.eku.EKU.domain.FreeBoard;
import com.eku.EKU.domain.FreeBoardComment;
import com.eku.EKU.exceptions.NoSuchStudentException;
import com.eku.EKU.form.CommentForm;
import com.eku.EKU.form.FreeBoardCommentResponse;
import com.eku.EKU.repository.FreeBoardCommentRepository;
import com.eku.EKU.repository.FreeBoardRepository;
import com.eku.EKU.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
    @Transactional
    public void writeComment(CommentForm form) throws IllegalStateException, EntityNotFoundException {
        FreeBoard article = freeBoardRepository.findById(form.getArticleID()).orElseThrow();
        FreeBoardComment comment = FreeBoardComment.builder()
                .content((form.getContent()))
                .writer(studentRepository.getById(form.getWriter()))
                .original(article)
                .build();
        freeBoardCommentRepository.save(comment);
        article.setComments(article.getComments()+1);
    }

    /**
     * 자유게시판댓글 db에서 요청한 댓글 삭제
     * @param form 삭제할 댓글의 정보가 들어있는 Form 객체
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    @Transactional
    public void deleteComment(CommentForm form) throws IllegalArgumentException, NoSuchElementException {
        freeBoardCommentRepository.deleteById(form.getCommentId());
        FreeBoard article = freeBoardRepository.findById(form.getArticleID()).orElseThrow();
        article.setComments(article.getComments()-1);
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

    /**
     * 자유게시판 게시물 id에 해당하는 댓글 List 반환
     * @param id
     * @return
     */
    public List<FreeBoardCommentResponse> commentList(Long id){
        List<FreeBoardComment> tempList = freeBoardCommentRepository.findAllByOriginalId(id);
        List<FreeBoardCommentResponse> list = new ArrayList<FreeBoardCommentResponse>();
        for(FreeBoardComment i : tempList){
            FreeBoardCommentResponse response = new FreeBoardCommentResponse();
            response.setContent(i.getContent());
            response.setWriter(i.getWriter().getStudNo());
            response.setFId(i.getFId());
            response.setWrittenTime(i.getWrittenTime());
            list.add(response);
        }
        return list;
    }
}
