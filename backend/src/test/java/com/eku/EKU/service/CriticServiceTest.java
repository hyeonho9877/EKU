package com.eku.EKU.service;

import com.eku.EKU.form.CriticLectureSearchResponse;
import com.eku.EKU.repository.CriticRepository;
import com.eku.EKU.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CriticServiceTest {

    @Autowired
    CriticService criticService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CriticRepository criticRepository;

    /*@Test
    void applyTest() {
        CriticForm criticForm = new CriticForm();
        criticForm.setEmail("ruldarm00@kyonggi.ac.kr");
        criticForm.setContent("ㅋㅋㅋㅋ 할많하않");
        criticForm.setGrade(Grade.A);
        criticForm.setLectureName("소프트웨어 공학");
        criticForm.setProfName("권기현");
        CriticResponse response = criticService.applyCritic(criticForm);

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(response.getContent()).isEqualTo(criticForm.getContent());
        soft.assertThat(response.getLectureName()).isEqualTo(criticForm.getLectureName());
        soft.assertThat(response.getProfName()).isEqualTo(criticForm.getProfName());
        soft.assertThat(response.getGrade()).isEqualTo(criticForm.getGrade());
        soft.assertAll();
    }*/

    /*@Test
    void applyTestNotValidWriter() {
        CriticForm criticForm = new CriticForm();
        criticForm.setEmail("notvalid@kyonggi.ac.kr");
        criticForm.setContent("ㅋㅋㅋㅋ 할많하않");
        criticForm.setGrade(Grade.A);
        criticForm.setLectureName("소프트웨어 공학");
        criticForm.setProfName("권기현");
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchStudentException.class, () -> criticService.applyCritic(criticForm));
    }*/

    /*@Test
    void removeCriticSuccess() {
        try {
            Critic target = criticRepository.findAll().stream().findFirst().orElseThrow();
            criticService.removeCritic(target.getCId());
        } catch (Exception e) {
            fail();
        }
    }*/

    /*@Test
    void removeCriticFailNotExistsID() {
        assertThrows(EmptyResultDataAccessException.class, () -> criticService.removeCritic(Long.MAX_VALUE));
    }*/

    /*@Test
    void updateCriticSuccess() {
        try {
            Critic target = criticRepository.findAll().stream().findFirst().orElseThrow();
            CriticForm criticForm = new CriticForm();
            criticForm.setCriticId(target.getCId());
            criticForm.setContent("헉 너무 재밌었어요 ㅠㅠ");

            criticService.updateCritic(criticForm);
            Critic afterUpdate = criticRepository.findById(target.getCId()).orElseThrow();
            org.assertj.core.api.Assertions.assertThat(afterUpdate.getContent()).isEqualTo(criticForm.getContent());
        } catch (Exception e) {
            fail();
        }
    }*/

    /*@Test
    void updateCriticFailNoId() {
        CriticForm criticForm = new CriticForm();
        criticForm.setContent("헉 너무 재밌었어요 ㅠㅠ");

        assertThrows(NoSuchElementException.class, () -> criticService.updateCritic(criticForm));
    }*/

    /*@Test
    void updateCriticFailNotValidId() {
        CriticForm criticForm = new CriticForm();
        criticForm.setCriticId(Long.MAX_VALUE);
        criticForm.setContent("헉 너무 재밌었어요 ㅠㅠ");

        assertThrows(NoSuchElementException.class, () -> criticService.updateCritic(criticForm));
    }*/

    @Test
    void searchTest(){
        List<CriticLectureSearchResponse> result = criticService.searchLectures("소프트웨어 공학");
        System.out.println(result);
    }
}
