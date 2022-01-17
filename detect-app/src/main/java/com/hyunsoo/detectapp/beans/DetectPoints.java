package com.hyunsoo.detectapp.beans;

import lombok.Getter;

@Getter
public enum DetectPoints {
    SAME_UUID_LOAN(1001, "다사용자_동일단말기_이체시도"),
    VPN_USER_TRANSFER(1002, "VPN_이용자_10분내_대출_시도");

    DetectPoints(int code, String val){
        this.code = code;
        this.val = val;
    }
    private int code;
    private String val;
}
