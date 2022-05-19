package com.eku.EKU.service;

import com.eku.EKU.domain.Schedule;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.ScheduleDataForm;
import com.eku.EKU.form.ScheduleForm;
import com.eku.EKU.form.ScheduleResponse;
import com.eku.EKU.repository.LectureRepository;
import com.eku.EKU.repository.ScheduleRepository;
import com.eku.EKU.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final LectureRepository lectureRepository;
    private final StudentRepository studentRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, LectureRepository lectureRepository, StudentRepository studentRepository) {
        this.scheduleRepository = scheduleRepository;
        this.lectureRepository = lectureRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * 시간표 삽입
     * @param forms
     * @return
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    public ScheduleResponse insertSchedule(List<ScheduleForm> forms)throws IllegalArgumentException, NoSuchElementException {
        List<Schedule> testList = scheduleRepository.findAllByStudentAndPassword(studentRepository.findById(forms.get(0).getStudNo()).orElseThrow(), forms.get(0).getPassword());
        if(!testList.isEmpty()) {
            ScheduleDataForm tempForm = new ScheduleDataForm();
            tempForm.setStudNo(forms.get(0).getStudNo());
            tempForm.setPassword(forms.get(0).getPassword());
            deleteSchedule(tempForm);
        }
        for(ScheduleForm i : forms){
            Student student = studentRepository.findById(i.getStudNo()).orElseThrow();
            Schedule schedule = Schedule.builder()
                    .student(student)
                    .password(i.getPassword())
                    .lectureName(i.getLecture_name())
                    .lectureRoom(i.getLecture_room())
                    .lectureTime(i.getLecture_time())
                    .professor(i.getProfessor())
                    .build();
            scheduleRepository.save(schedule);
        }
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setStudNo(forms.get(0).getStudNo());
        scheduleResponse.setPassword(forms.get(0).getPassword());
        return scheduleResponse;
    }

    /**
     * 삭제
     * @param form
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    public void deleteSchedule(ScheduleDataForm form)throws IllegalArgumentException, NoSuchElementException{
        Student student = studentRepository.findById(form.getStudNo()).orElseThrow();
        List<Schedule> schedules = scheduleRepository.findAllByStudentAndPassword(student, form.getPassword());
        if(schedules.isEmpty())
            throw new NoSuchElementException();
        for(Schedule i : schedules){
            scheduleRepository.delete(i);
        }
    }


    /**
     * 다운로드
     * @param form
     * @return
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    public List<ScheduleForm> loadSchedule(ScheduleDataForm form)throws IllegalArgumentException, NoSuchElementException{
        List<ScheduleForm> responseList = new ArrayList<ScheduleForm>();
        List<Schedule> list = scheduleRepository.findAllByStudentAndPassword(studentRepository.findById(form.getStudNo()).orElseThrow(), form.getPassword());
        if(list.isEmpty())
            throw new NoSuchElementException();
        for(Schedule i : list){
            ScheduleForm temp = new ScheduleForm();
            temp.setStudNo(i.getStudent().getStudNo());
            temp.setPassword(i.getPassword());
            temp.setLecture_name(i.getLectureName());
            temp.setLecture_time(i.getLectureTime());
            temp.setLecture_room(i.getLectureRoom());
            temp.setProfessor(i.getProfessor());
            responseList.add(temp);
        }
        return responseList;
    }
}


