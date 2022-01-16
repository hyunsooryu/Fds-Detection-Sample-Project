package com.hyunsoo.detectapp.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash(value = "customer-status", timeToLive = 60 * 10) //10분 캐시
@Getter
@Setter
@ToString
public class CustomerStatus {
    @Id
    private Long custId;

    private String vpnUseYn;

    private Long UUID;

    private LocalDateTime createdDate;

    private String loginYn;
}
