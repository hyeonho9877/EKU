package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.FreeBoard;
import com.eku.eku_ocr_test.domain.Student;
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
                .id(Long.parseLong("4"))
                .student(Student.builder().studNo((long)201713883).name("dd").department("dd").email("dd").build())
                .department("컴퓨터공학부")
                .title("제목")
                .content("내용")
                .time(currentTime)
                .build();
        freeBoardRepository.save(freeBoard);
    }

    @Test
    public void testDB2() {
        FreeBoard freeBoard = freeBoardRepository.findFreeBoardById((long) 1).orElseThrow();
        System.out.println(freeBoard);
    }
}
