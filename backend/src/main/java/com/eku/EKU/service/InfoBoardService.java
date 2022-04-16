package com.eku.EKU.service;

import com.eku.EKU.domain.*;
import com.eku.EKU.form.FreeBoardForm;
import com.eku.EKU.form.InfoBoardForm;
import com.eku.EKU.repository.InfoBoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
     * @return List<InfoBoard>로 목록을 반환
     */
    public ArrayList<BoardList> boardList()throws IllegalArgumentException, NoSuchElementException{
        List<InfoBoard> list = infoBoardRepository.findAll();
        ArrayList<BoardList> newList = new ArrayList<BoardList>();
        for(InfoBoard i : list){
            BoardList form = BoardList.builder()
                    .id(i.getId())
                    .title(i.getTitle())
                    .name(i.getWriter().getName())
                    .department(i.getDepartment())
                    .no(i.getWriter().getStudNo())
                    .build();
            newList.add(form);
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
                .id(newId())

                .department(form.getDepartment())
                .title(form.getTitle())
                .content(form.getContent())
                .writtenTime(currentTime()).build();
        return new InfoBoardResponse(infoBoardRepository.save(infoBoard));
    }
    /**
     * 새로운 게시물 id를 정하는 메소드
     * @return 마지막게시물의 id+1
     */
    public Long newId(){
        List<InfoBoard> list = infoBoardRepository.findAll();
        Long Id;
        if(list.size()==0)
            Id=(long)1;
        else
            Id = (long)list.get(list.size()-1).getId()+1;
        return Id;
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
}
