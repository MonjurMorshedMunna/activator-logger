package com.logger.activatorlogger.repositories;

import com.logger.activatorlogger.entities.ActivityLogger;
import com.logger.activatorlogger.entities.Role;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 02-Aug-17.
 */
@Repository
public interface ActivityLoggerRepository extends CrudRepository<ActivityLogger, Timestamp> {
  public List<ActivityLogger> findByUserId(String userId);

  @Query("select * from activity_logger where role_id=?0 allow filtering")
  public List<ActivityLogger> findByRoleId(Long roleId);

  @Query("select * from activity_logger where role_id>=0 allow filtering" )
  public List<ActivityLogger> findAllWithoutNullRole();
}
