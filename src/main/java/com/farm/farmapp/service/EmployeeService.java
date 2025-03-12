package com.farm.farmapp.service;

import com.farm.farmapp.models.Employees;
import com.farm.farmapp.models.FarmSystem;
import com.farm.farmapp.repository.EmployeeRepository;
import com.farm.farmapp.repository.FarmSystemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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
    public Employees createEmployee(Long farmId, Employees employees) {
        validateEmployee(farmId, employees);
        //Checks if the FarmSystem with the given id exists
        FarmSystem farm = farmSystemRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("Farm does not exist"));
        employees.setFarmSystem(farm);
        return employeeRepository.save(employees);
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

    public List<Employees> getAllEmployee(){
        return employeeRepository.findAll();
    }

    public Optional<Employees> GetEmployeeById(Long id){
        return employeeRepository.findById(id);
    }

    public Employees UpdateEmployeeDetail(Long id, Long farmId, Employees employeesDetail){

        if (!employeeRepository.existsById(id)) {
            // Throw an exception if it doesn't exist
            throw new IllegalArgumentException("Employee You Are Trying To Access Does Not Exist");
        }

        if (!farmSystemRepository.existsById(farmId)) {
            // Throw an exception if it doesn't exist
            throw new IllegalArgumentException("Farm You Are Trying To Access Does Not Exist");
        }

        Employees employees = employeeRepository.findById(id).orElseThrow();

        if (!StringUtils.hasText(employeesDetail.getFirstName())) {
            employees.setFirstName(employees.getFirstName());
        }else{
            employees.setFirstName(employeesDetail.getFirstName());
        }
        if (!StringUtils.hasText(employeesDetail.getLastName())) {
            employees.setLastName(employees.getLastName());
        }else{
            employees.setLastName(employeesDetail.getLastName());
        }
        if (!StringUtils.hasText(employeesDetail.getPosition())) {
            employees.setPosition(employees.getPosition());
        }else {
            employees.setPosition(employeesDetail.getPosition());
        }

        employees.setFarmSystem(employeesDetail.getFarmSystem());

        FarmSystem farm = farmSystemRepository.findById(farmId)
                .orElseThrow(() -> new IllegalArgumentException("Farm does not exist"));
        employees.setFarmSystem(farm);
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
