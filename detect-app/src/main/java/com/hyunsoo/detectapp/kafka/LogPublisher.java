package com.hyunsoo.detectapp.kafka;

import com.hyunsoo.detectapp.beans.DataBox;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LogPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(DataBox dataBox){
        applicationEventPublisher.publishEvent(dataBox);
    }
}
