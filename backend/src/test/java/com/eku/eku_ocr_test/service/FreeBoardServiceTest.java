package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.FreeBoard;
import com.eku.eku_ocr_test.repository.FreeBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;


@SpringBootTest
public class FreeBoardServiceTest {
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    @Test
    public void testDB(){
        FreeBoard freeBoard = FreeBoard.builder()
                .id(Long.parseLong("2"))
                .studNo(Long.parseLong("201611843"))
                .department("컴퓨터공학과")
                .title("제목")
                .content("내용")
                .time(new Date(2022,4,9))
                .view(123)
                .build();
        freeBoardRepository.save(freeBoard);
    }
    @Test
    public void testDB2(){
        FreeBoard freeBoard = freeBoardRepository.findFreeBoardById(Long.parseLong("1")).orElseThrow();
        System.out.println(freeBoard);
    }
}
