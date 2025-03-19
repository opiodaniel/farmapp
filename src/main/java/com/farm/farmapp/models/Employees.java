package com.farm.farmapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String title;
    private String position;
    private int salary;

    @ManyToOne
    @JoinColumn(name = "farm_id" , referencedColumnName = "id")
    private FarmSystem farmSystem;

    @ManyToMany(mappedBy = "employees")
    private Set<Activities> activities = new HashSet<>();

    public Long getId(){
        return id;
    }

    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName =firstName;
    }

    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName ;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosition(){
        return position;
    }
    public void setPosition(String position){
        this.position =position;
    }

    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public FarmSystem getFarmSystem(){
        return farmSystem;
    }
    public void setFarmSystem(FarmSystem farmSystem){
        this.farmSystem = farmSystem;
    }

    public Set<Activities> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activities> activities) {
        this.activities = activities;
    }
}
