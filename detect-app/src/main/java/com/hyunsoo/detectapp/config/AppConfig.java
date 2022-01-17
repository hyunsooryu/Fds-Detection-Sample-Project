package com.hyunsoo.detectapp.config;


import com.hyunsoo.detectapp.beans.Condition;
import com.hyunsoo.detectapp.beans.Criteria;
import com.hyunsoo.detectapp.beans.DetectPoints;
import com.hyunsoo.detectapp.beans.SearchKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Parameter;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Configuration
public class AppConfig implements WebMvcConfigurer {

    private static final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

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
                Criteria criteria = new Criteria(0, null,null,null, SearchKey.NONE);
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
                        if("1".equals(v[0])){
                            criteria.setSort(Sort.by("detectPointId").descending());
                        }
                    }else if("$custId".equals(k)){
                        criteria.setCustId(Long.valueOf(v[0]));
                    }else if("$detectPointCd".equals(k)){
                        if("1001".equals(v[0])){
                            criteria.setDetectPoints(DetectPoints.SAME_UUID_LOAN);
                        }else if("1002".equals(v[1])){
                            criteria.setDetectPoints(DetectPoints.VPN_USER_TRANSFER);
                        }
                    }
                });
                return criteria;
            }
        });
    }

}
