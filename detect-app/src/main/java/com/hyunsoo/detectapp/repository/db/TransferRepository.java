package com.hyunsoo.detectapp.repository.db;

import com.hyunsoo.detectapp.beans.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
