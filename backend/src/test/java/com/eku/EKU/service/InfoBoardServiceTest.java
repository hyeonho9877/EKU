package com.eku.EKU.service;

import com.eku.EKU.controller.BoardController;
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
        //infoBoard.setId((long)127);
        infoBoard.setWriterNo((long)201611843);
        infoBoard.setName("name");
        infoBoard.setDepartment("dd");
        infoBoard.setTitle("dd");
        infoBoard.setContent("dd");
        infoBoard.setBuilding("1000000000");
        System.out.println(boardController.insertBoard(infoBoard).getBody());

    }

    @Test
    public  void test3(){
        //System.out.println(infoBoardService.test(1));
    }
    @Test
    public void testDB() {
        FreeBoardForm freeBoard = new FreeBoardForm();
        //freeBoard.setId((long)1);
        //freeBoard.setTime(boardService.currentTime());
        freeBoard.setContent("dd");
        freeBoard.setDepartment("22");
        freeBoard.setTitle("dd");
        freeBoard.setWriterNo((long)20161183);
        System.out.println(boardController.insertBoard(freeBoard).getBody());
        //System.out.println(boardController.updateBoard(freeBoard));
        //System.out.println(boardController.loadBoard(freeBoard).getBody());
        //System.out.println(boardController.boardList().getBody());
        //System.out.println(boardController.deleteBoard(freeBoard));
    }
}
