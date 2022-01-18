package com.hyunsoo.detectapp.interceptors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunsoo.detectapp.beans.DetectPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;


public class ModelInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public ModelInterceptor(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Object obj = modelAndView.getModel().getOrDefault("renderJson", null);
        if(!Objects.isNull(obj)){
            modelAndView.getModel().remove("renderJson");
            String renderJson = objectMapper.writeValueAsString(obj);
            modelAndView.getModel().put("renderJson", renderJson);
        }
    }
}
