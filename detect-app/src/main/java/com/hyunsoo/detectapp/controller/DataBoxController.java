package com.hyunsoo.detectapp.controller;

import com.hyunsoo.detectapp.beans.DataBox;
import com.hyunsoo.detectapp.beans.DetectApiException;
import com.hyunsoo.detectapp.beans.Menu;
import com.hyunsoo.detectapp.beans.Status;
import com.hyunsoo.detectapp.service.StatusService;
import com.hyunsoo.detectapp.service.TransferService;
import com.hyunsoo.detectapp.utils.StatusUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class DataBoxController {

    private final StatusService statusService;

    private final TransferService transferService;

    @PostMapping("/data")
    //TODO KAFKA 서버에 모든 데이터를 보내도록 한다. - KAFKA - ELASTIC SEARCH
    /**
     * Kafka.Produce("") ASYNC 로직으로 EventListener를 최대한 활용하여 비동기 처리
     * 로그 수집
     */
    public DataBox insertDataBox(@RequestBody DataBox dataBox){

        if(Menu.LOGIN == dataBox.getMenu()) {
            Status status = StatusUtil.convertToStatus(dataBox);
            status.setLoginYn("Y");
            statusService.updateStatus(status);
        }
        else{
           if(!isLogin(dataBox)){
               throw new DetectApiException(HttpStatus.BAD_REQUEST, "로그인 해야 접근 할 수 있습니다.");
           }
           if(Menu.TRANSFER == dataBox.getMenu()){
               transferService.transfer(dataBox);
           }else{

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
        Map<String,String> errorBox = Collections.singletonMap("message", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(errorBox);
    }

}
