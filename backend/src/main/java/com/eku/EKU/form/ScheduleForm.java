package com.eku.EKU.form;

import lombok.Data;

@Data
public class ScheduleForm {
    private String lecture_time;
    private String lecture_room;
    private String professor;
    private String lecture_name;
    private long studNo;
    private long password;
}
