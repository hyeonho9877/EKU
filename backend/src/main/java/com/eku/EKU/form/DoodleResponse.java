package com.eku.EKU.form;

import com.eku.EKU.domain.Doodle;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class DoodleResponse {
    private long doodleId;
    private String content;
    private String minor;
    private String writtenTime;
    private String building;

    public DoodleResponse(DoodleForm form) {
        this.doodleId = form.getDoodleId();
        this.content = form.getContent();
        this.minor = form.getMinor();
        this.writtenTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.building = "";
    }

    public DoodleResponse(Doodle doodle) {
        this.doodleId = doodle.getDoodleId();
        this.content = doodle.getContent();
        this.minor = doodle.getBuilding().getBeaconId();
        this.building = doodle.getBuilding().getDesc();
        this.writtenTime = doodle.getWrittenTime();
    }
}
