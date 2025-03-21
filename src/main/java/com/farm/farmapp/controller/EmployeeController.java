package com.farm.farmapp.controller;

import com.farm.farmapp.DTO.ApiResponse;
import com.farm.farmapp.models.Employees;
import com.farm.farmapp.models.FarmSystem;
import com.farm.farmapp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/add_employee")
    public  ResponseEntity<ApiResponse<Employees>> EnterEmployee(@Valid @RequestBody Employees employees){
         Employees createdEmployee = employeeService.createEmployee(employees);
        return ResponseEntity.ok(new ApiResponse<>("Employee created successfully!", createdEmployee));
    }

    @PatchMapping("/assign_employee/{employeeId}/to_farm/{farmId}")
    public Employees AssignEmployee( @PathVariable Long employeeId,  @PathVariable Long farmId){
        return employeeService.AssignEmployee(employeeId, farmId);
    }

    @PatchMapping("/remove_employee/{employeeId}/from_farm")
    public Employees RemoveEmployee(@PathVariable Long employeeId){
        return employeeService.RemoveEmployee(employeeId);
    }


    @GetMapping
    public Page<Employees> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "salary") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeService.getAll(pageable);
    }

    @GetMapping("/get/{id}")
    public Optional<Employees> GetEmployeeById(@PathVariable Long id){
        return employeeService.GetEmployeeById(id);
    }
    @PutMapping("/update/employee/{employee_id}")
    public Employees UpdateEmployee(@PathVariable Long employee_id, @RequestBody Employees employees){
        return employeeService.UpdateEmployeeDetail(employee_id, employees);
    }
    @DeleteMapping("/delete/{id}")
    public String DeleteEmployee(@PathVariable Long id){
        employeeService.DeleteEmployee(id);
        return "Successfully Deleted Employee With Id: "+ id;
    }

}
