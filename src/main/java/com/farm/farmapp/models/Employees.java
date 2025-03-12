package com.farm.farmapp.models;

import jakarta.persistence.*;

@Entity
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String position;

    @ManyToOne
    @JoinColumn(name = "farm_id" , referencedColumnName = "id")
    private FarmSystem farmSystem;

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

    public String getPosition(){
        return position;
    }
    public void setPosition(String position){
        this.position =position;
    }

    public FarmSystem getFarmSystem(){
        return farmSystem;
    }
    public void setFarmSystem(FarmSystem farmSystem){
        this.farmSystem = farmSystem;
    }

}
