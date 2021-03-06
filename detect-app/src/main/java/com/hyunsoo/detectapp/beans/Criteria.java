package com.hyunsoo.detectapp.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Criteria{
    private int page;
    private Sort sort;
    private LocalDateTime from;
    private LocalDateTime to;
    private long custId;
    private DetectPoints detectPoints;

    private boolean byDefault;
    private boolean byCustId;
    private boolean byDetectPoints;
    private boolean byCustIdAndDetectPoints;
    private boolean byCustIdBetweenDates;
    private boolean byDetectPointsBetweenDates;
    private boolean byCustIdAndDetectPointBetweenDates;
    private boolean byBetweenDates;
}
