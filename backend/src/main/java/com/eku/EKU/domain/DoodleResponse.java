package com.eku.EKU.domain;

import lombok.Data;

@Data
public class DoodleResponse {
    private long doodleId;
    private String content;
    private String building;
    private String uuid;

    public DoodleResponse(Doodle doodle) {
        this.doodleId = doodle.getDoodleId();
        this.content = doodle.getContent();
        this.building = doodle.getBuilding().getDesc();
        this.uuid = doodle.getBuilding().getBeaconId();
    }
}
