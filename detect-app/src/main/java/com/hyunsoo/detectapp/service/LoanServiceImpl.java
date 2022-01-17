package com.hyunsoo.detectapp.service;

import com.hyunsoo.detectapp.aop.target.SAME_UUID_LOAN;
import com.hyunsoo.detectapp.beans.DataBox;
import com.hyunsoo.detectapp.beans.Loan;
import com.hyunsoo.detectapp.repository.db.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @SAME_UUID_LOAN
    @Override
    public boolean loan(DataBox dataBox){
        boolean result = true;
        try {
            Loan loan = new Loan();
            loan.setCustId(dataBox.getCustId());
            loan.setAmount(dataBox.getAmount());
            loan.setCreatedDate(dataBox.getCreatedDate());
            loanRepository.save(loan);
        }catch (Exception e){
            log.error("대출 테이블 저장 에러", e);
            result = false;
        }
        return result;
    }
}
