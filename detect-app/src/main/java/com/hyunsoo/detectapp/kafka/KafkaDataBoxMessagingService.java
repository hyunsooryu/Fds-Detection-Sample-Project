package com.hyunsoo.detectapp.kafka;

import com.hyunsoo.detectapp.beans.DataBox;

public interface KafkaDataBoxMessagingService{

    void sendDataBox(DataBox dataBox);
}
