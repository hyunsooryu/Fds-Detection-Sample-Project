package com.hyunsoo.detectapp.service;

import com.hyunsoo.detectapp.beans.Criteria;
import com.hyunsoo.detectapp.beans.DetectPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 탐지 데이터 조회 서비스
 * @author hyunsoo
 * 2022.01.17
 */
public interface DetectService {

    List<DetectPoint> getDetectPointList(Criteria criteria);

}
