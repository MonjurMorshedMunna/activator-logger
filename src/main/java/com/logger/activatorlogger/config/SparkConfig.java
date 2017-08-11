package com.logger.activatorlogger.config;

/*
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

*/
/**
 * Created by Monjur-E-Morshed on 03-Aug-17.
 *//*

@Configuration
public class SparkConfig {


  @Bean
  public SparkConf sparkConf() {
    SparkConf sparkConf = new SparkConf()
        .setAppName("StreamApp")
        .setMaster("master");

    return sparkConf;
  }

  @Bean
  public JavaSparkContext javaSparkContext() {
    return new JavaSparkContext(sparkConf());
  }

  @Bean
  public SparkSession sparkSession(){
      return SparkSession
              .builder()
              .sparkContext(javaSparkContext().sc())
              .appName("activity_logger")
              .getOrCreate();
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
      return new PropertySourcesPlaceholderConfigurer();
  }

}
*/
