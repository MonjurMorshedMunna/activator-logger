package com.logger.activatorlogger.entities;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Monjur-E-Morshed on 02-Aug-17.
 */
@Table(value = "activity_logger")
public class ActivityLogger {

  @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private Long id;

  @PrimaryKeyColumn(name = "user_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
  private String userId;

  @Column("access_time")
  private Timestamp accessTime;

  @Column("class_name")
  private String className;

  @Column("method_name")
  private String methodName;

  @Transient
  private static final AtomicLong count = new AtomicLong(0);

  public ActivityLogger() {
  }


  public Long getId() {
    return count.incrementAndGet();
  }

  public void setId(Long id) {
    this.id = null;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String pUserId) {
    userId = pUserId;
  }

  public Timestamp getAccessTime() {
    return accessTime;
  }

  public void setAccessTime(Timestamp pAccessTime) {
    accessTime = pAccessTime;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String pClassName) {
    className = pClassName;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String pMethodName) {
    methodName = pMethodName;
  }

}
