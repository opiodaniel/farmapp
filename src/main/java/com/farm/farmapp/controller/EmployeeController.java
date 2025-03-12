package com.farm.farmapp.controller;

import com.farm.farmapp.models.Employees;
import com.farm.farmapp.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/add_employee/{farmId}")
    public Employees EnterEmployee(@PathVariable Long farmId, @RequestBody Employees employees){
        return employeeService.createEmployee(farmId, employees);
    }

    @GetMapping("/get_all")
    public List<Employees> GetEmployees(){
        return employeeService.getAllEmployee();
    }
    @GetMapping("/get/{id}")
    public Optional<Employees> GetEmployeeById(@PathVariable Long id){
        return employeeService.GetEmployeeById(id);
    }
    @PutMapping("/update/{employee_id}/{farmId}")
    public Employees UpdateEmployee(@PathVariable Long employee_id, @PathVariable Long farmId, @RequestBody Employees employees){
        return employeeService.UpdateEmployeeDetail(employee_id, farmId, employees);
    }
    @DeleteMapping("/delete/{id}")
    public void DeleteEmployee(@PathVariable Long id){
        employeeService.DeleteEmployee(id);
    }

}
