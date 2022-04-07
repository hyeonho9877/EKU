package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.Critic;
import com.eku.eku_ocr_test.domain.Student;
import com.eku.eku_ocr_test.exceptions.NoSuchStudentException;
import com.eku.eku_ocr_test.form.CriticForm;
import com.eku.eku_ocr_test.repository.CriticRepository;
import com.eku.eku_ocr_test.repository.StudentRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class CriticService {

    private final StudentRepository studentRepository;
    private final CriticRepository criticRepository;

    public CriticService(StudentRepository studentRepository, CriticRepository criticRepository) {
        this.studentRepository = studentRepository;
        this.criticRepository = criticRepository;
    }

    public Critic applyCritic(CriticForm form) throws NoSuchStudentException, IllegalArgumentException, NoSuchElementException {

        Student writer = studentRepository.findStudentByEmail(form.getEmail()).orElseThrow(NoSuchStudentException::new);
        Critic critic = Critic.builder()
                .lectureName(form.getLectureName())
                .profName(form.getProfName())
                .content(form.getContent())
                .grade(form.getGrade())
                .writer(writer)
                .build();

        Critic result = criticRepository.save(critic);
        result.setWriter(null);
        return result;
    }

    public void removeCritic(long id) throws IllegalArgumentException, EmptyResultDataAccessException {
        criticRepository.deleteById(id);
    }

    @Transactional
    public void updateCritic(CriticForm form) throws EmptyResultDataAccessException, NoSuchElementException, NullPointerException {
        Critic target = criticRepository.findById(form.getCriticId()).orElseThrow();
        if (form.getContent() != null) {
            target.setContent(form.getContent());
        } else if (form.getGrade() != null) {
            target.setGrade(form.getGrade());
        } else if (form.getLectureName() != null) {
            target.setLectureName(form.getLectureName());
        } else if (form.getProfName() != null) {
            target.setProfName(form.getProfName());
        }
    }
}
