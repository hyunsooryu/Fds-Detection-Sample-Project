package com.hyunsoo.detectapp.kafka;

import com.hyunsoo.detectapp.beans.DataBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LogListener {

    private final KafkaDataBoxMessagingService kafkaDataBoxMessagingService;

    @Async
    @EventListener(DataBox.class)
    public void onApplicationEvent(DataBox event){
        try{
            //TODO KAFKA
           log.info("=======KAFKA LOGGING==========");
           log.info("{}",event.getCustId());
           log.info("{}",event.getCreatedDate());
           log.info("{}",event.getUuid());
           log.info("{}",event.getMenu());
           log.info("==============================");
           kafkaDataBoxMessagingService.sendDataBox(event);
        }catch(Exception e){
            log.error("로깅 이벤트 실패 - {}", e.getMessage());
        }

    }
}
