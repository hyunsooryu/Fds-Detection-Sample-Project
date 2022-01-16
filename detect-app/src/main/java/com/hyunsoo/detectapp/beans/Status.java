package com.hyunsoo.detectapp.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Status {
    @Id @Column(name="CUSTOMER_ID")
    private Long custId;
    @Column(name="UUID")
    private Long uuid;
    @Column(name="VPN_USE_YN")
    private String vpnUseYn;
    @Column(name="CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name="LOGIN_YN")
    private String loginYn;
}
