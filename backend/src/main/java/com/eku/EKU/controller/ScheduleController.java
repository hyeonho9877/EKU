package com.eku.EKU.controller;

import com.eku.EKU.form.ScheduleForm;
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

    @PostMapping("/schedule/write")
    public ResponseEntity<?> insertSchedule(@RequestBody ScheduleForm[] form){
        try{
            return ResponseEntity.ok(scheduleService.insertSchedule(form));
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
