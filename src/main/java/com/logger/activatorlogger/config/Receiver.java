package com.logger.activatorlogger.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Monjur-E-Morshed on 30-Jul-17.
 */
@Component
public class Receiver {

  private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  public CountDownLatch getLatch() {
    return latch;
  }

  @KafkaListener(topics = "ums_logger")
  public void receive(ConsumerRecord<?, ?> consumerRecord) {
    //logger.info("received data = '{}'", consumerRecord.toString());
    logger.info(consumerRecord.value().toString());
    latch.countDown();
  }

}
