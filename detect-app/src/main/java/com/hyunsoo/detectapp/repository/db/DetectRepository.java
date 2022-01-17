package com.hyunsoo.detectapp.repository.db;

import com.hyunsoo.detectapp.beans.DetectPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectRepository extends JpaRepository<DetectPoint, Long> {
}
