package com.hyunsoo.detectapp.kafka;

import com.hyunsoo.detectapp.beans.DataBox;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaDataBoxMessagingServiceImpl implements KafkaDataBoxMessagingService {

    private final KafkaTemplate<String, DataBox> kafkaTemplate;

    @Override
    public void sendDataBox(DataBox dataBox) {
        kafkaTemplate.send("databox.topic", dataBox);
    }
}
