package com.logger.activatorlogger.config;

import com.logger.activatorlogger.entities.ActivityLogger;
import com.logger.activatorlogger.repositories.ActivityLoggerRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Monjur-E-Morshed on 30-Jul-17.
 */
@Component
public class Receiver {

  private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  @Autowired
  private ActivityLoggerRepository activityLoggerRepository;

  public CountDownLatch getLatch() {
    return latch;
  }

  @KafkaListener(topics = "ums_logger")
  public void receive(ConsumerRecord<?, ?> consumerRecord) {
    //logger.info("received data = '{}'", consumerRecord.toString());
    logger.info(consumerRecord.toString());
    SparkConf conf = new SparkConf().setAppName("StreamApp").setMaster("master");
    JavaSparkContext sc = new JavaSparkContext(conf);
    List<String> logs = Arrays.asList(consumerRecord.value().toString().split(" "));
    ActivityLogger activityLogger = new ActivityLogger();
   
    latch.countDown();
  }

}
