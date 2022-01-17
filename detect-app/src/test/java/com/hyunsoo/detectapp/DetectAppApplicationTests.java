package com.hyunsoo.detectapp;

import com.hyunsoo.detectapp.beans.*;
import com.hyunsoo.detectapp.repository.db.DetectRepository;
import com.hyunsoo.detectapp.repository.db.LoanRepository;
import com.hyunsoo.detectapp.repository.db.StatusRepository;
import com.hyunsoo.detectapp.repository.db.TransferRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    //PagingTest
    @Test
    @DisplayName("탐지 데이터에 대한 페이징 쿼리 처리 테스트 - 등록순")
    void pagingTestAsc(){
        Pageable pageable = PageRequest.of(0, 2);
        detectRepository.findAll(pageable).stream()
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("탐지 데이터에 대한 페이징 쿼리 처리 테스트 - 최신순")
    void pagingTestDesc(){
        Pageable pageable = PageRequest.of(0, 2);

    }

    @Test
    void insertFreeData(){
        DetectPoint detectPoint = new DetectPoint();
        detectPoint.setCreatedDate(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        detectPoint.setCustId(23397L);
        detectPoint.setDetectPointCd(DetectPoints.SAME_UUID_LOAN.getCode());
        detectPoint.setDetectPoint(DetectPoints.SAME_UUID_LOAN.getVal());
        detectRepository.save(detectPoint);
    }

    @Test
    @DisplayName("탐지 데이터에 대한 페이징 쿼리 처리 테스트 - 검색 - custId")
    void pagingTestSearchCustId(){
       detectRepository.findAllByCreatedDateBetween(
               LocalDateTime.now().minusMinutes(10),
               LocalDateTime.now(),
               PageRequest.of(0, 2))
               .forEach(System.out::println);
    }

    @Test
    @DisplayName("탐지 데이터에 대한 페이징 쿼리 처리 테스트 - 검색 - code")
    void pagingTestSearchDetectPointCode(){
        detectRepository.findAllByDetectPointCd(1002, PageRequest.of(
                0, 2)
        ).stream().forEach(System.out::println);
    }




}
