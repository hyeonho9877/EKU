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
    @Test
    public void testDB2() {
        FreeBoardForm freeBoard = new FreeBoardForm();
        freeBoard.setId((long)1);
        System.out.println(boardController.loadBoard(freeBoard).getBody());
    }


    @Test
    public void testDB3() {
        freeBoardRepository.deleteAll();
    }

}
