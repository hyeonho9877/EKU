package com.eku.EKU.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class LectureGroup {
    @Id
    @Column(name = "group_id", nullable = false)
    private String groupID;
    @Column(name = "group_name", nullable = false)
    private String groupName;
    @Column(name = "campus_type", nullable = false)
    private String campusType;
    @Column(name = "group_sub_type", nullable = false)
    private String groupSubType;
}
