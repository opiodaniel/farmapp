package com.farm.farmapp.repository;

import com.farm.farmapp.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    @Query("SELECT a FROM AuditLog a WHERE a.tableName = :tableName ORDER BY a.timestamp DESC")
    List<AuditLog> findByTableName(@Param("tableName") String tableName);
}

