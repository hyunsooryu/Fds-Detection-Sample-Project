package com.hyunsoo.detectapp.repository.db;

import com.hyunsoo.detectapp.beans.DetectPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DetectRepository extends JpaRepository<DetectPoint, Long> {

    //고객 번호로 조회
    Page<DetectPoint> findAllByCustId(long custId, Pageable pageable);

    //탐지주제값으로 조회
    Page<DetectPoint> findAllByDetectPointCd(int detectPointCd, Pageable pageable);

    //고객 번호와 탐지 주제값으로 조회
    Page<DetectPoint> findAllByCustIdAndDetectPointCd(long custId, int detectPointCd, Pageable pageable);

    //기간으로 조회
    Page<DetectPoint> findAllByCreatedDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    //고객번호로 설정 기간으로 조회
    Page<DetectPoint> findAllByCustIdAndCreatedDateBetween(long custId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    //탐지주제값으로 설정 기간으로 조회
    Page<DetectPoint> findAllByDetectPointCdAndCreatedDateBetween(int detectPointCd, LocalDateTime start, LocalDateTime end, Pageable pageable);

    //고객번호와 탐지주제값으로 설정 기간으로 조회
    Page<DetectPoint> findAllByCustIdAndDetectPointCdAndCreatedDateBetween(long custId, int detectPointCd, LocalDateTime start, LocalDateTime end, Pageable pageable);

}
