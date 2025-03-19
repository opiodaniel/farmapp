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
        FarmSystem farmSystem = farmSystemRepository.findById(farmId).orElseThrow();
        Employees employee = employeeRepository.findById(employee_id).orElseThrow();
        employee.setFarmSystem(farmSystem);
        return employeeRepository.save(employee);
    }

    public Employees RemoveEmployee(Long employee_id){
        Employees employee = employeeRepository.findById(employee_id).orElseThrow();
        employee.setFarmSystem(null);
        return employeeRepository.save(employee);
    }

    private void validateEmployee(Long farmId, Employees employees) {

        if (!farmSystemRepository.existsById(farmId)) {
            // Throw an exception if it doesn't exist
            throw new IllegalArgumentException("Farm You Are Trying To Access Does Not Exist");
        }
        if (!StringUtils.hasText(employees.getFirstName())) {
            throw new IllegalArgumentException("Employee First Name is required and cannot be blank");
        }
        if (!StringUtils.hasText(employees.getLastName())) {
            throw new IllegalArgumentException("Employee Last Name is required and cannot be blank");
        }
        if (!StringUtils.hasText(employees.getPosition())) {
            throw new IllegalArgumentException("Employee Position field cannot be blank");
        }
    }

    public Page<Employees> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Optional<Employees> GetEmployeeById(Long id){
        return employeeRepository.findById(id);
    }

    public Employees UpdateEmployeeDetail(Long employee_id, Employees employeesDetail) {

        if (!employeeRepository.existsById(employee_id)) {
            throw new IllegalArgumentException("Employee You Are Trying To Access Does Not Exist");
        }

        Employees employees = employeeRepository.findById(employee_id).orElseThrow();

        // Map of field updaters to avoid redundant if-else statements
        Map<String, BiConsumer<Employees, String>> fieldUpdaters = Map.of(
                "firstName", Employees::setFirstName,
                "lastName", Employees::setLastName,
                "position", Employees::setPosition
        );

        for (Map.Entry<String, BiConsumer<Employees, String>> entry : fieldUpdaters.entrySet()) {
            String fieldValue = switch (entry.getKey()) {
                case "firstName" -> employeesDetail.getFirstName();
                case "lastName" -> employeesDetail.getLastName();
                case "position" -> employeesDetail.getPosition();
                default -> null;
            };

            if (StringUtils.hasText(fieldValue)) {
                entry.getValue().accept(employees, fieldValue);
            }
        }

        return employeeRepository.save(employees);
    }


    public void DeleteEmployee(Long id){
        validateDeleteEmployee(id);
        employeeRepository.deleteById(id);
    }

    private void validateDeleteEmployee(Long id) {
        // Check if the FarmSystem with the given id exists
        if (!farmSystemRepository.existsById(id)) {
            // Throw an exception if it doesn't exist
            throw new IllegalArgumentException("Employee You Are Trying To Delete Does Not Exist");
        }
    }


}
