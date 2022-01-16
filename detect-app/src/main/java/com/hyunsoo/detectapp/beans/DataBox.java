package com.hyunsoo.detectapp.beans;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class DataBox {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    private Menu menu;
    private long custId;
    private long amount;
    private String vpnUserYn;
    private Long uuid;
}
