package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class InternalLog extends AbstractEntity {

    @Column
    private LocalDateTime time;

    @Column
    private Double randomNumber;

    @Column
    private Long durationMinute;

    public InternalLog() {}

    public InternalLog(Double randomNumber, Long durationMinute) {
        this.randomNumber = randomNumber;
        this.durationMinute = durationMinute;
    }

}
