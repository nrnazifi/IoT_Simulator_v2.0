package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TimeBasedData extends AbstractEntity {

    @Column(nullable = false)
    private long parentId; // parent is foreign table

    @Column(nullable = false)
    private String parentFieldName;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private DayTypeEnum dayType;

    @Column(nullable = false)
    private Integer valueDay;

    @Column(nullable = false)
    private Integer valueNight;
}
