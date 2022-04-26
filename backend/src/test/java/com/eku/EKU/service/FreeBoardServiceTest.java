package com.eku.EKU.service;

import com.eku.EKU.controller.BoardController;
import com.eku.EKU.form.FreeBoardForm;
import com.eku.EKU.repository.FreeBoardRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class FreeBoardServiceTest {
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    @Autowired
    private FreeBoardService boardService;
    @Autowired
    private BoardController boardController;
    @Test
    public void testDB() {
        FreeBoardForm freeBoard = new FreeBoardForm();
        //freeBoard.setId(1);
        freeBoard.setTitle("title");
        freeBoard.setContent("content");
        freeBoard.setDepartment("컴공");
        freeBoard.setStudNo((long) 201713924);
        //freeBoard.setTime(boardService.currentTime());
        System.out.println(boardController.insertBoard(freeBoard).getBody());
        //System.out.println(boardController.updateBoard(freeBoard));
        //System.out.println(boardController.loadBoard(freeBoard));
        //System.out.println(boardController.boardList().getBody());
        //System.out.println(boardController.deleteBoard(freeBoard));
    }
    /*@Test
    public void testDB2() {
        System.out.println(boardService.studentNo((long)201611843));
    }
    @Test
    public void testDB3() {
        freeBoardRepository.deleteAll();
    }*/

}
