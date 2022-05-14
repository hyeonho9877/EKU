package com.eku.EKU.service;


import com.eku.EKU.form.BoardList;
import com.eku.EKU.domain.InfoBoard;
import com.eku.EKU.form.InfoBoardResponse;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.InfoBoardForm;
import com.eku.EKU.repository.InfoBoardRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InfoBoardService {
    public final InfoBoardRepository infoBoardRepository;

    public InfoBoardService(InfoBoardRepository infoBoardRepository) {
        this.infoBoardRepository = infoBoardRepository;
    }

    /**
     * 게시물 불러오기
     * @param form
     * @return
     */
    public InfoBoard loadBoard(InfoBoardForm form)throws IllegalArgumentException, NoSuchElementException {
        InfoBoard board = infoBoardRepository.findInfoBoardById(form.getId()).get();
        return board;
    }
    /**
     * 게시물 목록
     * @param boardForm 해당 강의동
     * @return List<InfoBoard>로 목록을 반환
     */
    public ArrayList<BoardList> boardList(InfoBoardForm boardForm)throws IllegalArgumentException, NoSuchElementException{
        List<InfoBoard> list = infoBoardRepository.findAll();
        ArrayList<BoardList> newList = new ArrayList<BoardList>();
        for(InfoBoard i : list){
            if(isCorrectBuilding(i.getBuilding(), boardForm.getLecture_building())){
                BoardList form = BoardList.builder()
                    .id(i.getId())
                    .title(i.getTitle())
                    .name(i.getName())
                    .department(i.getDepartment())
                    .no(i.getNo().getStudNo())
                    .build();
                newList.add(form);
            }
        }
        return newList;
    }
    /**
     * 게시판정보 삽입
     * @param form 삽입할 게시판의 정보
     * @return
     */
    public InfoBoardResponse insertBoard(InfoBoardForm form) throws IllegalArgumentException{
        Student studNo = Student.builder().studNo(form.getWriterNo()).name("temp").email("temp").department("temp").build();
        InfoBoard infoBoard = InfoBoard.builder()
                .no(studNo)
                .name(form.getName())
                .department(form.getDepartment())
                .title(form.getTitle())
                .content(form.getContent())
                .writtenTime(currentTime())
                .building(form.getBuilding())
                .build();
        return new InfoBoardResponse(infoBoardRepository.save(infoBoard));
    }
    /**
     * 게시판정보 수정
     * @param form 수정할 게시판의 정보
     */
    public void updateBoard(InfoBoardForm form) throws IllegalArgumentException, NoSuchElementException{
        InfoBoard board = infoBoardRepository.findInfoBoardById(form.getId()).get();
        if(form.getTitle()!=null&&form.getContent()!=null) {
            board.setContent(form.getContent());
            board.setTitle(form.getTitle());
            board.setBuilding(form.getBuilding());
            infoBoardRepository.save(board);
        }
    }
    /**
     * 게시물 삭제
     * @param id 해당 게시물 번호
     */
    public void deleteBoard(Long id) throws EmptyResultDataAccessException,IllegalArgumentException, NoSuchElementException {
        infoBoardRepository.deleteById(id);
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
     * 1011001010 이런식으로 오는 강의동코드와 목록이 표시되어야할 강의동 번호가 일치하는지 검사하는 함수
     */
    public boolean isCorrectBuilding(String code, int no){

        if(no<1||no>10)
            return false;
        else if(code.substring(no-1,no).equals("1"))
            return true;
        return false;
    }
}
