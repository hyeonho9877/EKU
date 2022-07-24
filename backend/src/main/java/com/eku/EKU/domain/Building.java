package com.eku.EKU.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building {
    @Id
    @Column(name = "beacon_id", nullable = false)
    private String beaconId;
    @Column(name = "description", nullable = false)
    private String desc;
}
