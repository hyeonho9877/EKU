package com.eku.eku_ocr_test.service;

import com.eku.eku_ocr_test.domain.FreeBoard;
import com.eku.eku_ocr_test.domain.Student;
import com.eku.eku_ocr_test.form.BoardListForm;
import com.eku.eku_ocr_test.form.FreeBoardForm;
import com.eku.eku_ocr_test.repository.FreeBoardRepository;
import com.eku.eku_ocr_test.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * 게시판 정보를 불러오거나 수정, 삽입, 삭제하는 기능담당
 */
@Service
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final StudentRepository studentRepository;

    public FreeBoardService(FreeBoardRepository freeBoardRepository, StudentRepository studentRepository) {
        this.freeBoardRepository = freeBoardRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * 게시물 목록 
     * @return List<FreeBoard>로 목록을 반환
     */
    public ArrayList<BoardListForm> boardList(){
        List<FreeBoard> list = freeBoardRepository.findAll();
        ArrayList<BoardListForm> = new ArrayList<BoardListForm>newList;
        for(FreeBoard i : list){
            BoardListForm form = BoardListForm.builder().id(i.getId()).title(i.getTitle()).build();

        }

    }

    /**
     * 게시판정보 삽입
     * @param form 삽입할 게시판의 정보
     * @return 성공시 true, 실패시 false 반환
     */
   public boolean insertBoard(FreeBoardForm form){
        Student studNo = Student.builder().studNo(form.getStudNo()).name("temp").email("temp").department("temp").build();
        try {
            FreeBoard freeBoard = FreeBoard.builder()
                    .id(newId())
                    .student(studNo)
                    .department(form.getDepartment())
                    .title(form.getTitle())
                    .content(form.getContent())
                    .time(form.getTime()).build();
            freeBoardRepository.save(freeBoard);
        }catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    /**
     * 시간함수
     * @return 현재시간
     */
    public String currentTime(){
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }
    /**
     * 새로운 게시물 id를 정하는 메소드
     * @return 마지막게시물의 id+1
     */
    public Long newId(){
        List<FreeBoard> list = freeBoardRepository.findAll();
        Long Id;
        if(list.size()==0)
            Id=(long)1;
        else
            Id = (long)list.get(list.size()-1).getId()+1;
        return Id;
    }

}
