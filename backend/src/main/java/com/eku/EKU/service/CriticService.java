package com.eku.EKU.service;

import com.eku.EKU.domain.Critic;
import com.eku.EKU.domain.CriticResponse;
import com.eku.EKU.domain.Student;
import com.eku.EKU.exceptions.NoSuchStudentException;
import com.eku.EKU.form.CriticForm;
import com.eku.EKU.repository.CriticRepository;
import com.eku.EKU.repository.StudentRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * 강의평가에 대한 서비스 클래스
 */
@Service
public class CriticService {

    private final StudentRepository studentRepository;
    private final CriticRepository criticRepository;

    public CriticService(StudentRepository studentRepository, CriticRepository criticRepository) {
        this.studentRepository = studentRepository;
        this.criticRepository = criticRepository;
    }

    /**
     * DB에 강의평가 저장하는 메소드
     * @param form 저장하려는 강의평가의 정보가 담김 Form 객체
     * @return 저장에 성공한 Critic 객체
     * @throws NoSuchStudentException
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    public CriticResponse applyCritic(CriticForm form) throws NoSuchStudentException, IllegalArgumentException, NoSuchElementException {
        Student writer = studentRepository.findStudentByEmail(form.getEmail()).orElseThrow(NoSuchStudentException::new);
        Critic critic = Critic.builder()
                .content(form.getContent())
                .grade(form.getGrade())
                .star(form.getStar())
                .writer(writer)
                .build();

        return new CriticResponse(criticRepository.save(critic));
    }

    /**
     * DB에 저장된 강의평가를 삭제하는 메소드
     * @param id 삭제하려는 강의평가의 ID
     * @throws IllegalArgumentException
     * @throws EmptyResultDataAccessException
     */
    public void removeCritic(long id) throws IllegalArgumentException, EmptyResultDataAccessException {
        criticRepository.deleteById(id);
    }

    /**
     * 강의 평가를 수정하는 메소드
     * @param form 수정할 내용이 담김 Form 객체, CriticId는 필수 이외는 옵션
     * @throws EmptyResultDataAccessException
     * @throws NoSuchElementException
     * @throws NullPointerException
     */
    @Transactional
    public void updateCritic(CriticForm form) throws EmptyResultDataAccessException, NoSuchElementException, NullPointerException {
        Critic target = criticRepository.findById(form.getCriticId()).orElseThrow();
        if (form.getContent() != null) {
            target.setContent(form.getContent());
        } else if (form.getGrade() != null) {
            target.setGrade(form.getGrade());
        } else if (form.getStar() != 0) {
            target.setStar(form.getStar());
        }
    }

    /**
     * 최근 작성된 강의평가를 조회하는 메소드
     * @return 최근 10개의 강의평가 리스트
     */
    public List<CriticResponse> readRecentCritic(){
        return criticRepository.findAll(Pageable.ofSize(10)).getContent()
                .stream()
                .map(CriticResponse::new)
                .collect(Collectors.toList());
    }
}
