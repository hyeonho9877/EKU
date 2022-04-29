package com.eku.EKU.service;

import com.eku.EKU.controller.BoardController;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.FreeBoardForm;
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
        InfoBoardForm infoBoard = new InfoBoardForm();
        infoBoard.setId((long)6);
        System.out.println(boardController.loadBoard(infoBoard).getBody());

    }
    @Test
    public void test2(){
        InfoBoardForm form = new InfoBoardForm();
        form.setWriterNo((long)201611843);
        form.setContent("내용");
        form.setDepartment("컴공");
        form.setTitle("제목");
        form.setName("이현규");
        form.setBuilding(("0000000100"));
        boardController.insertBoard(form);
    }
    @Test
    public  void test3(){
        InfoBoardForm form = new InfoBoardForm();
        form.setId((long)1);
        boardController.loadBoard(form);
    }

}
