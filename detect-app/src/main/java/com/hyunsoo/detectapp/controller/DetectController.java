package com.hyunsoo.detectapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunsoo.detectapp.beans.Condition;
import com.hyunsoo.detectapp.beans.Criteria;
import com.hyunsoo.detectapp.beans.DetectPoint;
import com.hyunsoo.detectapp.service.DetectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/detect")
public class DetectController {

    private final DetectService detectService;

    private final ObjectMapper objectMapper;

    @GetMapping("/list")
    public String getDetectPointList(@Condition Criteria criteria, Model model) throws JsonProcessingException {
        log.info("정렬조건 : {}", criteria.toString());
        List<DetectPoint> detectPointList = detectService.getDetectPointList(criteria);

        String json = objectMapper.writeValueAsString(detectPointList);
        System.out.println(json);

        model.addAttribute("renderJson", detectPointList);
        return "main/board";
    }
}
