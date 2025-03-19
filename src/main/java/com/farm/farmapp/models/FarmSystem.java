package com.farm.farmapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class FarmSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String farmName;
    private String location;

    @OneToMany(mappedBy = "farmSystem" , cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Employees> employees = new HashSet<>();

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

    public Set<Employees> getEmployees(){
        return employees;
    }
    public void setEmployees(Set<Employees> employees){
        this.employees =employees;
    }


}
