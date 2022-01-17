package com.hyunsoo.detectapp.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "DETECT_POINT")
public class DetectPoint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DETECT_POINT_ID")
    private Long detectPointId;
    @Column(name="CUSTOMER_ID")
    private Long custId;
    @Column(name="DETECT_POINT")
    private String DetectPoint;
    @Column(name="CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name="DETECT_POINT_CODE")
    private Integer detectPointCd;
}
