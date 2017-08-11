package com.logger.activatorlogger.entities;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Monjur-E-Morshed on 02-Aug-17.
 */
@Table(value = "activity_logger")
public class ActivityLogger {

  @PrimaryKeyColumn(name="user_id", ordinal = 0, type=PrimaryKeyType.PARTITIONED)
  private String userId;

  @PrimaryKeyColumn(name="access_time", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private Date accessTime;

  @Column("class_name")
  private String className;

  @Column("method_name")
  private String methodName;


  @Column("role_id")
  private Long roleId;

  @Transient
  private static final AtomicLong count = new AtomicLong(0);

  public ActivityLogger() {
  }


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
    return userId;
  }

  public void setUserId(String pUserId) {
    userId = pUserId;
  }

  public Date getAccessTime() {
    return accessTime;
  }

  public void setAccessTime(Date pAccessTime) {
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
