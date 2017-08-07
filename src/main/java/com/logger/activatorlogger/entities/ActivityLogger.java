package com.logger.activatorlogger.entities;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.sql.Timestamp;

/**
 * Created by Monjur-E-Morshed on 02-Aug-17.
 */
@Table(value = "activity_logger")
public class ActivityLogger {

  @PrimaryKeyColumn(name = "time", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private Timestamp time;

  @PrimaryKeyColumn(name = "user_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
  private String userId;

  @Column("access_time")
  private Timestamp accessTime;

  @Column("class_name")
  private String className;

  @Column("method_name")
  private String methodName;


  public ActivityLogger() {
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp pTime) {
    time = pTime;
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
