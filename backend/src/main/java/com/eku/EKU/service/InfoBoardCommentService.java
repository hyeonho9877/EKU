package com.eku.EKU.service;

import com.eku.EKU.domain.InfoBoardComment;
import com.eku.EKU.exceptions.NoSuchCommentException;
import com.eku.EKU.form.CommentForm;
import com.eku.EKU.repository.InfoBoardCommentRepository;
import com.eku.EKU.repository.InfoBoardRepository;
import com.eku.EKU.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

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
                .original(infoBoardRepository.getById(form.getArticleID()))
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
}
