package com.hyunsoo.detectapp;

import com.hyunsoo.detectapp.beans.*;
import com.hyunsoo.detectapp.repository.db.DetectRepository;
import com.hyunsoo.detectapp.repository.db.LoanRepository;
import com.hyunsoo.detectapp.repository.db.StatusRepository;
import com.hyunsoo.detectapp.repository.db.TransferRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class DetectAppApplicationTests {

    private final StatusRepository statusRepository;
    private final TransferRepository transferRepository;
    private final DetectRepository detectRepository;
    private final LoanRepository loanRepository;

    @Autowired
    DetectAppApplicationTests(StatusRepository statusRepository, TransferRepository transferRepository,
                              DetectRepository detectRepository, LoanRepository loanRepository){
        this.statusRepository = statusRepository;
        this.transferRepository = transferRepository;
        this.detectRepository = detectRepository;
        this.loanRepository = loanRepository;
    }

}
