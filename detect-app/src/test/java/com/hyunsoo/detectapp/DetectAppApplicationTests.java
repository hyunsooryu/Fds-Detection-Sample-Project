package com.hyunsoo.detectapp;

import com.hyunsoo.detectapp.beans.CustomerStatus;
import com.hyunsoo.detectapp.beans.Status;
import com.hyunsoo.detectapp.repository.db.StatusRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class DetectAppApplicationTests {

    private final StatusRepository statusRepository;

    @Autowired
    DetectAppApplicationTests(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }

    @Test
    void updateTest() {
        Status status = new Status();
        status.setCustId(4L);
        status.setUuid(6L);
        status.setVpnUseYn("Y");
        status.setCreatedDate(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        System.out.println(status.getCreatedDate().toString());
        statusRepository.save(status);
    }

    @Test
    void findByCustIdTest(){
        Optional<Status> status = statusRepository.findById(1L);
        Assertions.assertThat(status).isNotNull();
        System.out.println(status.get());
    }

    @Test
    void findByUuidTest(){
        List<Status> statusList = statusRepository.findAllByUuidOrderByCreatedDateAsc(2L);
        statusList.forEach(System.out::println);
    }

    @Test
    void updateInRedisTestCustomerStatus(){
        CustomerStatus status = new CustomerStatus();
        status.setCreatedDate(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        status.setCustId(1L);
        status.setUUID(2L);
        status.setVpnUseYn("N");
    }





}
