package com.hyunsoo.detectapp.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunsoo.detectapp.beans.Condition;
import com.hyunsoo.detectapp.beans.Criteria;
import com.hyunsoo.detectapp.beans.DetectPoints;
import com.hyunsoo.detectapp.interceptors.ModelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.Sort;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Parameter;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private static final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final ObjectMapper objectMapper;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ModelInterceptor modelInterceptor = new ModelInterceptor(objectMapper);
        registry.addInterceptor(modelInterceptor)
                .addPathPatterns("/detect");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HandlerMethodArgumentResolver() {

            @Override
            public boolean supportsParameter(MethodParameter parameter) {
              return parameter.getParameter().getAnnotation(Condition.class) != null;
            }


            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
                Map<String,String[]> paramMap = webRequest.getParameterMap();
                Criteria criteria = new Criteria(
                        0,
                         Sort.by("detectPointId").descending(),
                        null,
                        null,
                        0,
                        null,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false);

                paramMap.forEach((k, v)->{
                    if("$page".equals(k)){
                        criteria.setPage(Integer.valueOf(v[0]));
                    }else if("$from".equals(k)){
                        LocalDateTime from = LocalDateTime.parse(v[0], dateTimeFormatter);
                        criteria.setFrom(from);
                    }else if("$to".equals(k)){
                        LocalDateTime to = LocalDateTime.parse(v[0], dateTimeFormatter);
                        criteria.setTo(to);
                    }else if("$listing".equals(k)){
                        //최신 순 정렬
                        if(!"1".equals(v[0])){
                            criteria.setSort(Sort.by("detectPointId").ascending());
                        }
                    }else if("$custId".equals(k)){
                        criteria.setCustId(Long.valueOf(v[0]));
                    }else if("$detectPointCd".equals(k)){
                        if("1001".equals(v[0])){
                            criteria.setDetectPoints(DetectPoints.SAME_UUID_LOAN);
                        }else if("1002".equals(v[0])){
                            criteria.setDetectPoints(DetectPoints.VPN_USER_TRANSFER);
                        }
                    }
                });

                boolean byDefault = true;
                //고객은 없고 탐지만 있는 경우
                if(criteria.getCustId() <= 0 && criteria.getDetectPoints() != null){
                    if(criteria.getFrom() == null && criteria.getTo() == null){ //날짜 없음
                        criteria.setByDetectPoints(true);
                        byDefault = false;
                    }else{//날짜 있음
                        criteria.setByDetectPointsBetweenDates(true);
                        byDefault = false;

                    }
                }
                //탐지는 없고 고객만 있는 경우

                else if(criteria.getDetectPoints() == null && criteria.getCustId() > 0){
                    if(criteria.getFrom() == null && criteria.getTo() == null){ //날짜 없음
                        criteria.setByCustId(true);
                        byDefault = false;

                    }else{//날짜 있음
                        criteria.setByCustIdBetweenDates(true);
                        byDefault = false;
                    }
                }
                //고객도 없고 탐지도 없는 경우
                else if(criteria.getCustId() <= 0 && criteria.getDetectPoints() == null){
                    if(criteria.getFrom() == null && criteria.getTo() == null){ //날짜 없음

                    }else{//날짜 있음
                        criteria.setByBetweenDates(true);
                        byDefault = false;
                    }
                }
                //둘다 있는 경우
                else{
                    if(criteria.getFrom() == null && criteria.getTo() == null){ //날짜 없음
                        criteria.setByCustIdAndDetectPoints(true);
                        byDefault = false;
                    }else{//날짜 있음
                        criteria.setByCustIdAndDetectPointBetweenDates(true);
                        byDefault = false;
                    }
                }

                criteria.setByDefault(byDefault);
                return criteria;
            }
        });
    }

}
