package com.farm.farmapp.service;

import com.farm.farmapp.models.FarmSystem;
import com.farm.farmapp.repository.FarmSystemRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        validateFarmSystem(farmSystem);
        return farmSystemRepository.save(farmSystem);
    }


    private void validateFarmSystem(FarmSystem farmSystem) {
        if (farmSystem == null) {
            throw new IllegalArgumentException("FarmSystem object cannot be null");
        }
        if (!StringUtils.hasText(farmSystem.getFarmName())) {
            throw new IllegalArgumentException("Farm name is required and cannot be blank");
        }
        if (!StringUtils.hasText(farmSystem.getLocation())) {
            throw new IllegalArgumentException("Location is required and cannot be blank");
        }
    }

    //Getting all
    public List<FarmSystem> GetAllFarmSystem(){
        return farmSystemRepository.findAll();
    }
    //Get One
    public Optional<FarmSystem> GetFarmSystemById(Long id){
        validateGetFarmSystemById(id);
        return farmSystemRepository.findById(id);
    }
    private void validateGetFarmSystemById(Long id) {
        // Check if the FarmSystem with the given id exists
        if (!farmSystemRepository.existsById(id)) {
            // Throw an exception if it doesn't exist
            throw new IllegalArgumentException("Farm does not exist");
        }
    }
    //Update By ID
    public FarmSystem UpdateFarmSystem(Long id, FarmSystem farmSystemDetail){

//        validateUpdateFarmSystem(id, farmSystemDetail);
        if (!farmSystemRepository.existsById(id)) {
            // Throw an exception if it doesn't exist
            throw new IllegalArgumentException("Farm You Are Trying To Update Does Not Exist");
        }
        FarmSystem farmSystem = farmSystemRepository.findById(id).orElseThrow();

        if (!StringUtils.hasText(farmSystemDetail.getFarmName())) {
            farmSystem.setFarmName(farmSystem.getFarmName());
        }else {
            farmSystem.setFarmName(farmSystemDetail.getFarmName());
        }

        if (!StringUtils.hasText(farmSystemDetail.getLocation())) {
            farmSystem.setLocation(farmSystem.getLocation());
        }else{
            farmSystem.setLocation(farmSystemDetail.getLocation());
        }

        return farmSystemRepository.save(farmSystem);
    }

    // Delete FarmSystem
    public void DeleteFarmSystem(Long id){
        validateDeleteFarmSystem(id);
        farmSystemRepository.deleteById(id);

    }

    private void validateDeleteFarmSystem(Long id) {
        // Check if the FarmSystem with the given id exists
        if (!farmSystemRepository.existsById(id)) {
            // Throw an exception if it doesn't exist
            throw new IllegalArgumentException("Farm You Are Trying To Delete Does Not Exist");
        }
    }

}
