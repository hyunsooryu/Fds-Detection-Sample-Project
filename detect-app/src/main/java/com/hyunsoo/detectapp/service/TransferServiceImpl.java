package com.hyunsoo.detectapp.service;

import com.hyunsoo.detectapp.aop.target.SAME_UUID_LOAN;
import com.hyunsoo.detectapp.aop.target.VPN_USER_TRANSFER;
import com.hyunsoo.detectapp.beans.DataBox;
import com.hyunsoo.detectapp.beans.Transfer;
import com.hyunsoo.detectapp.repository.db.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    @VPN_USER_TRANSFER
    @Override
    public boolean transferAmount(DataBox dataBox) {

        Transfer transfer = new Transfer();

        transfer.setCustId(dataBox.getCustId());
        transfer.setCreatedDate(dataBox.getCreatedDate());
        transfer.setAmount(dataBox.getAmount());

        boolean result = true;
        log.info("custId : {} transfer_amount : {}", transfer.getCustId(), transfer.getAmount());
        try {
            transferRepository.save(transfer);
        }catch (Exception e){
            log.error("이체 실패", e);
            result = false;
        }
        return result;
    }
}
