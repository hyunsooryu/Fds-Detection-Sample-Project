package com.hyunsoo.detectapp.service;

import com.hyunsoo.detectapp.aop.target.SAME_UUID_LOAN;
import com.hyunsoo.detectapp.beans.DataBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransferServiceImpl implements TransferService {

    @SAME_UUID_LOAN
    @Override
    public boolean transfer(DataBox dataBox) {
        log.info("transfer complete : {}", dataBox.toString());
        return true;
    }
}
