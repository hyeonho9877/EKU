package com.eku.EKU.service;

import com.eku.EKU.controller.FreeBoardController;
import com.eku.EKU.domain.FreeBoard;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.FreeBoardForm;
import com.eku.EKU.repository.FreeBoardRepository;
import com.eku.EKU.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;


@SpringBootTest
public class FreeBoardServiceTest {
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FreeBoardService boardService;
    @Autowired
    private FreeBoardController boardController;
    @Test
    public void testDB() {
        FreeBoardForm freeBoard = new FreeBoardForm();
        //freeBoard.setId((long) 1);
        freeBoard.setTitle("title");
        freeBoard.setContent("content");
        freeBoard.setDepartment("컴공");
        freeBoard.setStudNo((long) 201713883);
        freeBoard.setTime(boardService.currentTime());
        System.out.println(boardController.insertBoard(freeBoard).getBody());
        //System.out.println(boardController.updateBoard(freeBoard));
        //System.out.println(boardController.loadBoard(freeBoard));
        System.out.println(boardController.boardList().getBody());
        //System.out.println(boardController.deleteBoard(freeBoard));
    }
    @Test
    public void testDB2() {
        List<FreeBoard> list = freeBoardRepository.findAll();
        Long id = list.get(list.size()-1).getId();
        System.out.println(id);
    }
    @Test
    public void testDB3() {
        freeBoardRepository.deleteAll();
    }

}
