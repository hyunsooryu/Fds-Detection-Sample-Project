package com.hyunsoo.detectapp.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name="LOAN")
public class Loan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOAN_ID")
    private Long loanId;
    @Column(name = "CUSTOMER_ID")
    private Long custId;
    @Column(name = "AMOUNT")
    private Long amount;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
}
