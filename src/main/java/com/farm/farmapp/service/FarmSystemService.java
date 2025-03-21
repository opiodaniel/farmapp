package com.farm.farmapp.service;

import com.farm.farmapp.models.FarmSystem;
import com.farm.farmapp.repository.FarmSystemRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class FarmSystemService {

    private final FarmSystemRepository farmSystemRepository;

    public FarmSystemService(FarmSystemRepository farmSystemRepository){
        this.farmSystemRepository = farmSystemRepository;
    }

    // Creating
    public FarmSystem CreateFarmSystem(FarmSystem farmSystem){
        return farmSystemRepository.save(farmSystem);
    }

    //Getting all
    public List<FarmSystem> GetAllFarmSystem(){
        return farmSystemRepository.findAll();
    }
    //Get One
    public Optional<FarmSystem> GetFarmSystemById(Long id){
        FarmSystem existingFarm = farmSystemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + id));
        return farmSystemRepository.findById(id);
    }

    //Update By ID
    public FarmSystem UpdateFarmSystem(Long id, FarmSystem farmSystemDetails) {
        FarmSystem existingFarm = farmSystemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + id));

        // Dynamically update only non-null fields
        for (Field field : FarmSystem.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object newValue = field.get(farmSystemDetails);
                if (newValue != null) {
                    field.set(existingFarm, newValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error updating field: " + field.getName(), e);
            }
        }

        return farmSystemRepository.save(existingFarm);
    }


//    public FarmSystem UpdateFarmSystem(Long id, FarmSystem farmSystemDetail){
//
//        farmSystemRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Farm You Are Trying To Update Does Not Exist"));
//        FarmSystem farmSystem = farmSystemRepository.findById(id).orElseThrow();
//
//        if (!StringUtils.hasText(farmSystemDetail.getFarmName())) {
//            farmSystem.setFarmName(farmSystem.getFarmName());
//        }else {
//            farmSystem.setFarmName(farmSystemDetail.getFarmName());
//        }
//
//        if (!StringUtils.hasText(farmSystemDetail.getLocation())) {
//            farmSystem.setLocation(farmSystem.getLocation());
//        }else{
//            farmSystem.setLocation(farmSystemDetail.getLocation());
//        }
//
//        return farmSystemRepository.save(farmSystem);
//    }

    // Delete FarmSystem
    public void DeleteFarmSystem(Long id){
        FarmSystem existingFarm = farmSystemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Farm not found with id: " + id));
        farmSystemRepository.deleteById(id);

    }

}
