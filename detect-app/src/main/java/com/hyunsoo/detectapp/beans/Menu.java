package com.hyunsoo.detectapp.beans;

public enum Menu {
    TRANSFER(0), LOAN(1), LOGIN(2);
    private int code;

    Menu(int code){
        this.code = code;
    }
}
