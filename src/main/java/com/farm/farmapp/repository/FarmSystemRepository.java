package com.farm.farmapp.repository;

import com.farm.farmapp.models.FarmSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmSystemRepository extends JpaRepository<FarmSystem,  Long> {
}
