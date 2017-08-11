package com.logger.activatorlogger.repositories;

import com.logger.activatorlogger.entities.Role;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("select * from role")
    List<Role> findAllRoles();
}
