package com.farm.farmapp.repository;
import com.farm.farmapp.models.EmployeeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleCloudRepository extends JpaRepository<EmployeeFile, Long> {
}

