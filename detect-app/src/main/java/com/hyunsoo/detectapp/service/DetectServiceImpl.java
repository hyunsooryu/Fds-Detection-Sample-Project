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
        Pageable pageable = null;
        int page = criteria.getPage();
        Sort sort = criteria.getSort();
        if(sort == null){
            pageable = PageRequest.of(page, detectProperties.getQueryPageSize());
        }else{
            pageable = PageRequest.of(page, detectProperties.getQueryPageSize(), sort);
        }

        //TODO criteria 기준 분기 처리
        //Searching이 아닐 때,
        if(criteria.isByDefault()){
            try {
                Page<DetectPoint> detectPointPage = detectRepository.findAll(pageable);
                if(detectPointPage != null) detectPointList = detectPointPage.getContent();
            }catch (Exception e){
                log.error("DB 조회 실패 - 조건 : {}", criteria.toString(), e);
            }
        }
        if(criteria.isByCustId()){
            try{
                Page<DetectPoint> detectPointPage = detectRepository.findAllByCustId(criteria.getCustId(), pageable);
                if(detectPointPage != null) detectPointList = detectPointPage.getContent();
            }catch (Exception e){
                log.error("DB 조회 실패 - 조건 : {}", criteria.toString(), e);
            }
        }
        if(criteria.isByDetectPoints()){
            try{
                Page<DetectPoint> detectPointPage = detectRepository.findAllByDetectPointCd(criteria.getDetectPoints().getCode(), pageable);
                if(detectPointPage != null) detectPointList = detectPointPage.getContent();
            }catch (Exception e){
                log.error("DB 조회 실패 - 조건 : {}", criteria.toString(), e);
            }
        }
        if(criteria.isByBetweenDates()){
            try{
                Page<DetectPoint> detectPointPage = detectRepository.findAllByCreatedDateBetween(criteria.getFrom(), criteria.getTo(), pageable);
                if(detectPointPage != null) detectPointList = detectPointPage.getContent();
            }catch (Exception e){
                log.error("DB 조회 실패 - 조건 : {}", criteria.toString(), e);
            }
        }
        if(criteria.isByCustIdBetweenDates()){
            try{
                Page<DetectPoint> detectPointPage = detectRepository.findAllByCustIdAndCreatedDateBetween(criteria.getCustId(), criteria.getFrom(), criteria.getTo(), pageable);
                if(detectPointPage != null) detectPointList = detectPointPage.getContent();
            }catch (Exception e){
                log.error("DB 조회 실패 - 조건 : {}", criteria.toString(), e);
            }
        }
        if(criteria.isByDetectPointsBetweenDates()){
            try{
                Page<DetectPoint> detectPointPage = detectRepository.findAllByDetectPointCdAndCreatedDateBetween(criteria.getDetectPoints().getCode(), criteria.getFrom(), criteria.getTo(), pageable);
                if(detectPointPage != null) detectPointList = detectPointPage.getContent();
            }catch (Exception e){
                log.error("DB 조회 실패 - 조건 : {}", criteria.toString(), e);
            }
        }

        if(criteria.isByCustIdAndDetectPoints()){
            try{
                Page<DetectPoint> detectPointPage = detectRepository.findAllByCustIdAndDetectPointCd(criteria.getCustId(), criteria.getDetectPoints().getCode(), pageable);
                if(detectPointPage != null) detectPointList = detectPointPage.getContent();
            }catch (Exception e){
                log.error("DB 조회 실패 - 조건 : {}", criteria.toString(), e);
            }
        }

        if(criteria.isByCustIdAndDetectPointBetweenDates()){
            try{
                Page<DetectPoint> detectPointPage = detectRepository.findAllByCustIdAndDetectPointCdAndCreatedDateBetween(criteria.getCustId(),criteria.getDetectPoints().getCode(), criteria.getFrom(), criteria.getTo(), pageable);
                if(detectPointPage != null) detectPointList = detectPointPage.getContent();
            }catch (Exception e){
                log.error("DB 조회 실패 - 조건 : {}", criteria.toString(), e);
            }
        }

        return detectPointList;
    }

}
