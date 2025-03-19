package com.farm.farmapp.repository;

import com.farm.farmapp.models.EmployeeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeFileRepository extends JpaRepository<EmployeeFile, Long> {
}

