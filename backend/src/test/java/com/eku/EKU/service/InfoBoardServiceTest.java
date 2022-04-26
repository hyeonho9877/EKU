package com.eku.EKU.service;

import com.eku.EKU.controller.BoardController;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.InfoBoardForm;
import com.eku.EKU.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InfoBoardServiceTest {
    @Autowired
    private BoardController boardController;
    @Autowired
    private InfoBoardService infoBoardService;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void test1(){
        InfoBoardForm form = new InfoBoardForm();
        form.setLecture_building(1);
        System.out.println(boardController.boardList(form));

    }
    @Test
    public void test2(){
        InfoBoardForm form = new InfoBoardForm();
        form.setWriterNo((long)201611843);
        form.setContent("내용");
        form.setDepartment("컴공");
        form.setTitle("제목");
        form.setName("이현규");
        form.setBuilding((long)1000000000);
        boardController.insertBoard(form);
    }


}
