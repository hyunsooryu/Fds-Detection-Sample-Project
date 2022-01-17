package com.hyunsoo.detectapp.service;

import com.hyunsoo.detectapp.beans.Criteria;
import com.hyunsoo.detectapp.beans.DetectPoint;
import com.hyunsoo.detectapp.config.DetectProperties;
import com.hyunsoo.detectapp.repository.db.DetectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DetectServiceImpl implements DetectService {

    private final DetectProperties detectProperties;

    private final DetectRepository detectRepository;

    @Override
    public List<DetectPoint> getDetectPointList(Criteria criteria) {

        List<DetectPoint> detectPointList = Collections.EMPTY_LIST;

        //TODO criteria 기준 분기 처리

        return detectPointList;
    }

}
