package com.hyunsoo.detectapp.repository.db;

import com.hyunsoo.detectapp.beans.Status;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    List<Status> findAllByUuidOrderByCreatedDateAsc(long uuid);
}
