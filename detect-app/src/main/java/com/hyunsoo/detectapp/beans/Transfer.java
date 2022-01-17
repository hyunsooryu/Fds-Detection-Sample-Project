package com.hyunsoo.detectapp.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Transfer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TRANSFER_ID")
    private Long transferId;

    @Column(name="AMOUNT")
    private Long amount;

    @Column(name="CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name="CUSTOMER_ID")
    private Long custId;
}
