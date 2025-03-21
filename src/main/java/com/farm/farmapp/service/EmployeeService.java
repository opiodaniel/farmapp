package com.farm.farmapp.service;

import com.farm.farmapp.models.Activities;
import com.farm.farmapp.models.Employees;
import com.farm.farmapp.models.FarmSystem;
import com.farm.farmapp.repository.EmployeeRepository;
import com.farm.farmapp.repository.FarmSystemRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;

@Service
public class EmployeeService {

    private final FarmSystemRepository farmSystemRepository;
    private final EmployeeRepository employeeRepository;

    // Constructor injection
    public EmployeeService(FarmSystemRepository farmSystemRepository, EmployeeRepository employeeRepository) {
        this.farmSystemRepository = farmSystemRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Employees createEmployee(Employees employees) {
        return employeeRepository.save(employees);
    }

    public Employees AssignEmployee(Long employee_id, Long farmId){
        FarmSystem farmSystem = farmSystemRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + farmId));
        Employees employee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + employee_id));
        employee.setFarmSystem(farmSystem);
        return employeeRepository.save(employee);
    }

    public Employees RemoveEmployee(Long employee_id){
        Employees employee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + employee_id));
        employee.setFarmSystem(null);
        return employeeRepository.save(employee);
    }

//    private void validateEmployee(Long farmId, Employees employees) {
//
//        if (!farmSystemRepository.existsById(farmId)) {
//            // Throw an exception if it doesn't exist
//            throw new IllegalArgumentException("Farm You Are Trying To Access Does Not Exist");
//        }
//        if (!StringUtils.hasText(employees.getFirstName())) {
//            throw new IllegalArgumentException("Employee First Name is required and cannot be blank");
//        }
//        if (!StringUtils.hasText(employees.getLastName())) {
//            throw new IllegalArgumentException("Employee Last Name is required and cannot be blank");
//        }
//        if (!StringUtils.hasText(employees.getPosition())) {
//            throw new IllegalArgumentException("Employee Position field cannot be blank");
//        }
//    }

    public Page<Employees> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Optional<Employees> GetEmployeeById(Long id){
        Employees existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
        return employeeRepository.findById(id);
    }

    public Employees UpdateEmployeeDetail(Long employee_id, Employees employeesDetail) {

        Employees existingEmployee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + employee_id));

        // Dynamically update only non-null fields
        for (Field field : Employees.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object newValue = field.get(employeesDetail);
                if (newValue != null) {
                    field.set(existingEmployee, newValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error updating field: " + field.getName(), e);
            }
        }

        return employeeRepository.save(existingEmployee);
    }

    public void DeleteEmployee(Long id) {
        Employees existingEmployee= employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
        employeeRepository.deleteById(id);
    }

}
