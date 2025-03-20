package com.farm.farmapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="activities")
public class Activities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String activityName;
    String location;

    @ManyToMany()
    @JoinTable(name="activity_employee", joinColumns = @JoinColumn(name="activity_id"), inverseJoinColumns = @JoinColumn(name="employee_id"))
    @JsonIgnore
    //@JsonBackReference
    private Set<Employees> employees = new HashSet<>();


    public Long getId(){
        return id;
    }

    public String getActivityName(){
        return activityName;
    }
    public void setActivityName(String activityName){
        this.activityName =activityName;
    }

    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public Set<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employees> employees) {
        this.employees = employees;
    }
}
