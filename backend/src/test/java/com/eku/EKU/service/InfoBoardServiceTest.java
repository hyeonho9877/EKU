package com.eku.EKU.service;

import com.eku.EKU.controller.BoardController;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.BoardListForm;
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
        BoardListForm form = new BoardListForm();
        form.setLecture_building(1);
        form.setPage(0);
        System.out.println(boardController.infoBoardList(form));
    }
    @Test
    public  void test3(){
        //System.out.println(infoBoardService.test(1));
    }

}
