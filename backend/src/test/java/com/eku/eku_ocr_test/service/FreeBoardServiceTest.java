package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.FreeBoard;
import com.eku.eku_ocr_test.repository.FreeBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
public class FreeBoardServiceTest {
    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Test
    public void testDB() {
        FreeBoard freeBoard;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        freeBoard = FreeBoard.builder()
                .id(Long.parseLong("1"))
                .studNo(Long.parseLong("201611843"))
                .department("컴퓨터공학과")
                .title("제목")
                .content("내용")
                .time(currentTime)
                .build();
        System.out.println(freeBoard);
        freeBoardRepository.delete(freeBoardRepository.findFreeBoardById((long) 1).get());
    }

    @Test
    public void testDB2() {
        FreeBoard freeBoard = freeBoardRepository.findFreeBoardById((long) 1).orElseThrow();
        System.out.println(freeBoard);
    }
}
