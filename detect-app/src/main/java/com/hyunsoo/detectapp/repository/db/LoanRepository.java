package com.hyunsoo.detectapp.repository.db;

import com.hyunsoo.detectapp.beans.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

}
