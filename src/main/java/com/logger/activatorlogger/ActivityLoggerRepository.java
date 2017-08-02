package com.logger.activatorlogger;

import com.logger.activatorlogger.entities.ActivityLogger;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 02-Aug-17.
 */
public interface ActivityLoggerRepository extends CrudRepository<ActivityLogger, Timestamp>{
  public List<ActivityLogger> findByUserId(String userId);
}
