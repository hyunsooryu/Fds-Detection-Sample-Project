package com.hyunsoo.detectapp.controller;

import com.hyunsoo.detectapp.beans.Condition;
import com.hyunsoo.detectapp.beans.Criteria;
import com.hyunsoo.detectapp.beans.DetectPoint;
import com.hyunsoo.detectapp.service.DetectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/detect")
public class DetectController {

    private final DetectService detectService;

    @ResponseBody
    @GetMapping("/test")
    public List<DetectPoint> getDetectPointList(@Condition Criteria criteria){
        log.info("정렬조건 : {}", criteria.toString());
        List<DetectPoint> detectPointList = detectService.getDetectPointList(criteria);
        return detectPointList;
    }
}
