package com.eku.EKU.service;

import com.eku.EKU.form.BoardListForm;
import com.eku.EKU.form.BoardListResponse;
import com.eku.EKU.domain.FreeBoard;
import com.eku.EKU.form.FreeBoardResponse;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.FreeBoardForm;
import com.eku.EKU.repository.FreeBoardRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * 게시판 정보를 불러오거나 수정, 삽입, 삭제하는 기능담당
 */
@Service
public class FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;

    public FreeBoardService(FreeBoardRepository freeBoardRepository) {
        this.freeBoardRepository = freeBoardRepository;
    }

    /**
     * 게시물 불러오기
     * @param form
     * @return
     */
    public FreeBoard loadBoard(FreeBoardForm form)throws IllegalArgumentException, NoSuchElementException{
        FreeBoard board = freeBoardRepository.findById(form.getId()).get();
        board.setView(board.getView()+1);
        freeBoardRepository.save(board);

        return board;
    }
    /**
     * 게시물 목록 
     * @return List<FreeBoard>로 목록을 반환
     */
    public List<BoardListResponse> boardList(BoardListForm listForm)throws IllegalArgumentException, NoSuchElementException{
        PageRequest pageRequest = PageRequest.of(listForm.getPage(), 8);
        Page<FreeBoard> list = freeBoardRepository.findAll(pageRequest);
        List<BoardListResponse> newList = new ArrayList<BoardListResponse>();
        for(FreeBoard i : list){
            BoardListResponse form = BoardListResponse.builder()
                    .id(i.getId())
                    .title(i.getTitle())
                    .no(studentNo(i.getStudent().getStudNo()))
                    .department(i.getDepartment())
                    .build();
            newList.add(form);
        }
        return newList;
    }
    /**
     * 게시판정보 수정
     * @param form 수정할 게시판의 정보
     */
    public void updateBoard(FreeBoardForm form) throws IllegalArgumentException, NoSuchElementException{
        FreeBoard board = freeBoardRepository.findById(form.getId()).get();
        if(form.getTitle()!=null&&form.getContent()!=null) {
            board.setContent(form.getContent());
            board.setTitle(form.getTitle());
            freeBoardRepository.save(board);
        }
    }

    /**
     * 게시물 삭제
     * @param id 해당 게시물 번호
     */
    public void deleteBoard(Long id) throws IllegalArgumentException, NoSuchElementException, EmptyResultDataAccessException {
        freeBoardRepository.deleteById(id);
    }
    
    /**
     * 게시판정보 삽입
     * @param form 삽입할 게시판의 정보
     * @return
     */
     public FreeBoardResponse insertBoard(FreeBoardForm form) throws IllegalArgumentException, NoSuchElementException{
        Student studNo = Student.builder().studNo(form.getWriterNo()).name("temp").email("temp").department("temp").build();
        FreeBoard freeBoard = FreeBoard.builder()
                .student(studNo)
                .department(form.getDepartment())
                .title(form.getTitle())
                .content(form.getContent())
                .time(currentTime()).build();
        return new FreeBoardResponse(freeBoardRepository.save(freeBoard));
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
     * 학번전체를 받아 3,4번째 자리만 추출
     * @param no 학번(2016xxxxx)
     * @return (ex-16)
     */
    public Long studentNo(Long no){
        Long temp = no/100000;
        return temp-2000;
    }
}
