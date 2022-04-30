package com.eku.EKU.service;

import com.eku.EKU.domain.Lecture;
import com.eku.EKU.domain.Schedule;
import com.eku.EKU.domain.Student;
import com.eku.EKU.form.ScheduleForm;
import com.eku.EKU.form.ScheduleResponse;
import com.eku.EKU.repository.LectureRepository;
import com.eku.EKU.repository.ScheduleRepository;
import com.eku.EKU.repository.StudentRepository;
import org.springframework.stereotype.Service;

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
     *
     */
    public ScheduleResponse insertSchedule(ScheduleForm[] forms)throws IllegalArgumentException, NoSuchElementException {
        for(ScheduleForm i : forms){
            Student student = studentRepository.getById(i.getStudNo());
            Lecture lecture = lectureRepository.findByPrototypeLectureNameAndPrototypeProfessor(i.getLecture_name(),i.getProfessor()).orElseThrow();
            Schedule schedule = Schedule.builder()
                    .lecture_id(lecture)
                    .studNo(student)
                    .password(i.getPassword())
                    .build();
            scheduleRepository.save(schedule);
        }
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setStudNo(forms[0].getStudNo());
        scheduleResponse.setPassword(forms[0].getPassword());
        return scheduleResponse;
    }
}
