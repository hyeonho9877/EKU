package com.eku.EKU.service;

import com.eku.EKU.domain.InfoBoardComment;
import com.eku.EKU.exceptions.NoSuchCommentException;
import com.eku.EKU.form.CommentForm;
import com.eku.EKU.form.InfoBoardCommentResponse;
import com.eku.EKU.repository.InfoBoardCommentRepository;
import com.eku.EKU.repository.InfoBoardRepository;
import com.eku.EKU.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class InfoBoardCommentService {
    private final InfoBoardRepository infoBoardRepository;
    private final InfoBoardCommentRepository commentRepository;
    private final StudentRepository studentRepository;

    public InfoBoardCommentService(InfoBoardRepository infoBoardRepository, InfoBoardCommentRepository commentRepository, StudentRepository studentRepository) {
        this.infoBoardRepository = infoBoardRepository;
        this.commentRepository = commentRepository;
        this.studentRepository = studentRepository;
    }

    public void applyComment(CommentForm form) throws IllegalArgumentException, EntityNotFoundException {
        InfoBoardComment comment = InfoBoardComment.builder()
                .content(form.getContent())
                .writer(studentRepository.getById(form.getWriter()))
                .original(infoBoardRepository.getById(form.getArticleId()))
                .build();

        commentRepository.save(comment);
    }

    public void deleteComment(CommentForm form) throws IllegalArgumentException {
        commentRepository.deleteById(form.getCommentId());
    }

    @Transactional
    public void updateComment(CommentForm form) throws IllegalArgumentException {
        InfoBoardComment target = commentRepository.findById(form.getCommentId()).orElseThrow(NoSuchCommentException::new);
        if (form.getContent() != null) {
            target.setContent(form.getContent());
        }
    }
    /**
     * 공지게시판 게시물 id에 해당하는 댓글 List 반환
     * @param id
     * @return
     */
    public List<InfoBoardCommentResponse> commentList(Long id){
        List<InfoBoardComment> tempList = commentRepository.findAllByOriginalId(id);
        List<InfoBoardCommentResponse> list = new ArrayList<InfoBoardCommentResponse>();
        for(InfoBoardComment i : tempList){
            InfoBoardCommentResponse response = new InfoBoardCommentResponse();
            response.setContent(i.getContent());
            response.setWriter(i.getWriter().getStudNo());
            response.setIId(i.getIcId());
            response.setWrittenTime(i.getWrittenTime());
            list.add(response);
        }
        return list;
    }
}
