package com.farm.farmapp.controller;

import com.farm.farmapp.models.FarmSystem;
import com.farm.farmapp.service.FarmSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/farm")
public class FarmSystemController {

    @Autowired
    private FarmSystemService farmSystemService;

    @PostMapping("/add_farm")
    public FarmSystem CreateFarmSystem(@RequestBody FarmSystem farmSystem){
        return farmSystemService.CreateFarmSystem(farmSystem);
    }
    @GetMapping("/get_all")
    public List<FarmSystem> GetAll(){
        return farmSystemService.GetAllFarmSystem();
    }
    @GetMapping("/get/{id}")
    public Optional<FarmSystem> GetById(@PathVariable Long id){
        return farmSystemService.GetFarmSystemById(id);
    }
    @PutMapping("/update/{id}")
    public FarmSystem UpdateFarm(@PathVariable Long id, @RequestBody FarmSystem farmSystem){
        return farmSystemService.UpdateFarmSystem(id, farmSystem);
    }
    @DeleteMapping("/delete/{id}")
    public String DeleteFarm(@PathVariable Long id){
        farmSystemService.DeleteFarmSystem(id);
        return "Successfully Deleted Farm";
    }

}
