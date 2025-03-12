package com.farm.farmapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class FarmSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String farmName;
    private String location;
    private String activities;

    @OneToMany(mappedBy = "farmSystem" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Employees> employees;

    public Long getId(){
        return id;
    }

    public String getFarmName(){
        return farmName;
    }
    public void setFarmName(String farmName){
        this.farmName =farmName;
    }

    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public String getActivities(){
        return activities;
    }
    public void setActivities(String activities){
        this.activities = activities;
    }

    public List<Employees> getEmployees(){
        return employees;
    }
    public void setEmployees(List<Employees> employees){
        this.employees =employees;
    }


}
