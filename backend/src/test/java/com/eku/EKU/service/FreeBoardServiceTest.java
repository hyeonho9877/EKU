package com.eku.EKU.service;

import com.eku.EKU.domain.FreeBoard;
import com.eku.EKU.domain.Student;
import com.eku.EKU.repository.FreeBoardRepository;
import com.eku.EKU.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class FreeBoardServiceTest {

    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testDB() {
        FreeBoard freeBoard;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        freeBoard = FreeBoard.builder()
                .id((long)5)
                .student(Student.builder().studNo((long)201713883).name("dd").department("dd").email("dd").build())
                .department("컴퓨터공학부")
                .title("제목")
                .content("내용")
                .view(1)
                .time(currentTime)
                .build();
        freeBoardRepository.save(freeBoard);
    }

    @Test
    public void testDB2() {
        Student student = Student.builder().studNo((long)201661843).name("이현규").department("컴퓨터공학부").email("iz100412@gmail.com").authenticated(true).build();

        studentRepository.save(student);
    }
}
