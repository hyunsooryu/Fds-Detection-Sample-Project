package com.hyunsoo.detectapp.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Scenario {

    @Id @Column(name = "SCENARIO_ID")
    private Integer scenarioId;
    @Column(name = "START_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
    private LocalDateTime from;
    @Column(name = "END_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
    private LocalDateTime to;
    @Column(name = "WORK_YN")
    private String workYn;
    @Column(name= "SCENARIO_NAME")
    private String scenarioName;

    @Transient
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @Transient
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

}
