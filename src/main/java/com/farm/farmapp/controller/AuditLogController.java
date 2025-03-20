package com.farm.farmapp.controller;

import com.farm.farmapp.models.AuditLog;
import com.farm.farmapp.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // Fetch logs for a specific table (e.g., "employees")
    @GetMapping("/{tableName}")
    public ResponseEntity<List<AuditLog>> getAuditLogs(@PathVariable String tableName) {
        List<AuditLog> logs = auditLogRepository.findByTableName(tableName);
        return ResponseEntity.ok(logs);
    }

    // Fetch all audit logs
    @GetMapping("/")
    public ResponseEntity<List<AuditLog>> getAllLogs() {
        return ResponseEntity.ok(auditLogRepository.findAll());
    }
}


// ===============CREATING THE LOG TABLE=======================

//CREATE TABLE audit_logs (
//        id SERIAL PRIMARY KEY,
//        table_name VARCHAR(255),
//action VARCHAR(50),
//record_id INT,
//timestamp TIMESTAMP DEFAULT NOW()
//);

// ==================INSERT===================

//CREATE OR REPLACE FUNCTION log_employee_insert() RETURNS TRIGGER AS $$
//BEGIN
//INSERT INTO audit_logs (table_name, action, record_id, timestamp)
//VALUES ('employees', 'INSERT', NEW.id, NOW());
//RETURN NEW;
//END;
//$$ LANGUAGE plpgsql;


//CREATE TRIGGER employee_insert_trigger
//AFTER INSERT ON employees
//FOR EACH ROW EXECUTE FUNCTION log_employee_insert();


// ===================DELETE=====================

//CREATE OR REPLACE FUNCTION log_employee_delete() RETURNS TRIGGER AS $$
//BEGIN
//INSERT INTO audit_logs (table_name, action, record_id, timestamp)
//VALUES ('employees', 'DELETE', OLD.id, NOW());
//RETURN OLD;
//END;
//$$ LANGUAGE plpgsql;
//
//
//CREATE TRIGGER employee_delete_trigger
//AFTER DELETE ON employees
//FOR EACH ROW EXECUTE FUNCTION log_employee_delete();

// ========================UPDATE=======================

//CREATE OR REPLACE FUNCTION log_employee_update() RETURNS TRIGGER AS $$
//BEGIN
//INSERT INTO audit_logs (table_name, action, record_id, timestamp)
//VALUES ('employees', 'UPDATE', NEW.id, NOW());
//RETURN NEW;
//END;
//$$ LANGUAGE plpgsql;

//CREATE TRIGGER employee_update_trigger
//AFTER UPDATE ON employees
//FOR EACH ROW EXECUTE FUNCTION log_employee_update();



