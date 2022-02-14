package com.hyunsoo.detectapp.controller;

import com.hyunsoo.detectapp.beans.DataBox;
import com.hyunsoo.detectapp.beans.DetectApiException;
import com.hyunsoo.detectapp.beans.Menu;
import com.hyunsoo.detectapp.beans.Status;
import com.hyunsoo.detectapp.kafka.LogPublisher;
import com.hyunsoo.detectapp.service.LoanService;
import com.hyunsoo.detectapp.service.StatusService;
import com.hyunsoo.detectapp.service.TransferService;
import com.hyunsoo.detectapp.utils.StatusUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class DataBoxController {

    private final StatusService statusService;

    private final TransferService transferService;

    private final LoanService loanService;

    private final LogPublisher logPublisher;

    @PostMapping("/data")
    //TODO KAFKA 서버에 모든 데이터를 보내도록 한다. - KAFKA - ELASTIC SEARCH
    /**
     * Kafka.Produce("") ASYNC 로직으로 EventListener를 최대한 활용하여 비동기 처리
     * 로그 수집
     */
    public DataBox insertDataBox(@RequestBody DataBox dataBox){

        //logging - 이벤트 발행
        logPublisher.publish(dataBox);

        //1. 메뉴가 로그인 일 때
        if(Menu.LOGIN == dataBox.getMenu()) {
            Status status = StatusUtil.convertToStatus(dataBox);
            status.setLoginYn("Y");
            statusService.updateStatus(status);
        }
        //2. 메뉴가 이체 혹은 대출 일때
        else{
           if(!isLogin(dataBox)){
               //로그인 정보가 없다면 Exception 처리를 한다. (로그인 정보 없을 시 이체 및 대출 불가)
               throw new DetectApiException(HttpStatus.BAD_REQUEST, "로그인 해야 접근 할 수 있습니다.");
           }
           if(Menu.TRANSFER == dataBox.getMenu()){
               //2-1. 메뉴가 이체일 때, 이체 서비스를 진행한다.
               transferService.transferAmount(dataBox);
           }else{
              //2-2. 메뉴가 대출일 때, 대출 서비스를 진행한다.
               loanService.loan(dataBox);
           }
        }

        return dataBox;
    }


    @GetMapping("/data/custId/{id}")
    public Status getDataByCustId(@PathVariable long id){
        return statusService.getStatusByCustId(id);
    }

    //보통 쿠키로 처리하지만, CACHE - DB로
    private boolean isLogin(DataBox dataBox){
        Status status = statusService.getStatusByCustId(dataBox.getCustId());
        if(Objects.isNull(status) || !"Y".equals(status.getLoginYn())){
            return false;
        }
        return true;
    }

    @ExceptionHandler(value = DetectApiException.class)
    public ResponseEntity<Map<String,String>> exceptionHandler(DetectApiException ex){
        return ResponseEntity
                .status(ex.getStatus())
                .body(Collections.singletonMap("message", ex.getMessage()));
    }

}
