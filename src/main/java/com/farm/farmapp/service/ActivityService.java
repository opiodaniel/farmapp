package com.farm.farmapp.service;

import com.farm.farmapp.models.Activities;
import com.farm.farmapp.models.Employees;
import com.farm.farmapp.repository.ActivityRepository;
import com.farm.farmapp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final EmployeeRepository employeeRepository;

    public ActivityService(ActivityRepository activityRepository, EmployeeRepository employeeRepository){
        this.activityRepository = activityRepository;
        this.employeeRepository = employeeRepository;
    }

    public Activities CreateActivity(Activities activities){
        return activityRepository.save(activities);
    }

    public Activities AssignActivity(Long activity_id, Long employee_id){

        Activities activities = activityRepository.findById(activity_id).orElseThrow(); // this activity obtained contains all employees assigned to it.

        Employees employee = employeeRepository.findById(employee_id).orElseThrow(); // get a employee from the database to be added to a set of employees doing  certain activity.

        Set<Employees> employees = activities.getEmployees(); //getting a set of all employees who do a specific activity;

        //If an activity has no employees assigned yet, activities.getEmployees() might return null.
        //In that case, trying to do employees.add(employee); would result in a NullPointerException.
        //If the employees set is null, we initialize it with a new HashSet<>(), so we can safely add employees.
        if (employees == null) {
            employees = new HashSet<>();
        }
        employees.add(employee); // Add new employee to the set already containing other employees.

        activities.setEmployees(employees); // Update the activity with the new set


        //List<Long> employeeIds = List.of(employee_id); // Convert single ID to a list
        //Set<Employees> employees = new HashSet<>(employeeRepository.findAllById(employeeIds));
        //activities.setEmployees(employees); // This line replaces the current list of employees with a new set containing only the single employee. This means each time you assign an employee, any previously assigned employees are removed.

        return activityRepository.save(activities);
    }

    public Activities RemoveActivity(Long activity_id, Long employee_id){

        Activities activities = activityRepository.findById(activity_id).orElseThrow();
        Employees employee = employeeRepository.findById(employee_id).orElseThrow(); // get a employee from the database to be removed from a set of employees doing  certain activity.

        Set<Employees> employees = activities.getEmployees(); //getting a set of all employees who do a specific activity;

        //If an activity has no employees assigned yet, activities.getEmployees() might return null.
        //In that case, trying to do employees.add(employee); would result in a NullPointerException.
        //If the employees set is null, we initialize it with a new HashSet<>(), so we can safely add employees.
        if (employees == null) {
            employees = new HashSet<>();
        }
        employees.remove(employee); // Add new employee to the set already containing other employees.

        activities.setEmployees(employees); // Update the activity with the new set

        return activityRepository.save(activities);
    }

    public List<Activities> getAllActivities(){
        return activityRepository.findAll();
    }

    public Optional<Activities> getActivity(Long id){
        return activityRepository.findById(id);
    }

    public void deleteActivity(Long id){
        activityRepository.deleteById(id);
    }

    public Activities updateActivity(Long id, Activities activityDetail){
        Activities activities = activityRepository.findById(id).orElseThrow();
        activities.setActivityName(activityDetail.getActivityName());
        activities.setLocation(activityDetail.getLocation());
        return activityRepository.save(activities);
    }

}
