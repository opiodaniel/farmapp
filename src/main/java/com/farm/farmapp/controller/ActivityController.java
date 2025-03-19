package com.farm.farmapp.controller;

import com.farm.farmapp.models.Activities;
import com.farm.farmapp.models.Employees;
import com.farm.farmapp.repository.EmployeeRepository;
import com.farm.farmapp.service.ActivityService;
import com.farm.farmapp.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService){
        this.activityService = activityService;
    }

    @PostMapping("/add_activity")
    public Activities createActivity(@RequestBody Activities activities){
        return activityService.CreateActivity(activities);
    }

    // Used patch mapping because it only adds to the existing set of employees without removing previous assignments.
    @PatchMapping("/assign_activity/{activity_id}/to_employee/{employee_id}")
    public Activities assignActivity(@PathVariable Long activity_id, @PathVariable Long employee_id){
        return activityService.AssignActivity(activity_id, employee_id);
    }

    // Used patch mapping because it only adds to the existing set of employees without removing previous assignments.
    @PatchMapping("/remove_activity/{activity_id}/from_employee/{employee_id}")
    public Activities removeActivity(@PathVariable Long activity_id, @PathVariable Long employee_id){
        return activityService.RemoveActivity(activity_id, employee_id);
    }


     @GetMapping("/get_all")
    public List<Activities> getAllActivities(){
        return activityService.getAllActivities();
    }

    @GetMapping("/get/{id}")
    public Optional<Activities> getActivity(@PathVariable Long id){
        return activityService.getActivity(id);
    }
     @PutMapping("/update/{activity_id}")
    public Activities updateActivity(@PathVariable Long activity_id, @RequestBody Activities activities){
        return activityService.updateActivity(activity_id, activities);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteActivity(Long id){
        activityService.deleteActivity(id);
    }

}
