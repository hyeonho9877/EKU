package com.eku.EKU.controller;

import com.eku.EKU.form.ScheduleDataForm;
import com.eku.EKU.form.ScheduleListForm;
import com.eku.EKU.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     *  시간표 삽입
     * @param listForm 시간표 폼 배열
     * @return
     */
    @PostMapping("/schedule/write")
    public ResponseEntity<?> insertSchedule(@RequestBody ScheduleListForm listForm){
        try{
            return ResponseEntity.ok(scheduleService.insertSchedule(listForm.getList()));
        }catch (NoSuchElementException | IllegalArgumentException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 시간표 삭제
     * @param form
     * @return
     */
    @PostMapping("/schdule/delete")
    public ResponseEntity<?> deleteSchedule(@RequestBody ScheduleDataForm form){
        try{
            scheduleService.deleteSchedule(form);
            return ResponseEntity.ok("Delete success");
        }catch (NoSuchElementException | IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     *
     * @param form
     * @return
     */
    @PostMapping("/schedule/view")
    public ResponseEntity<?> loadSchedule(@RequestBody ScheduleDataForm form){
        try{
            return ResponseEntity.ok(scheduleService.loadSchedule(form));
        }catch (NoSuchElementException | IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
