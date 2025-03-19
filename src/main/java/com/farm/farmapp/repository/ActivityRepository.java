package com.farm.farmapp.repository;

import com.farm.farmapp.models.Activities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activities, Long> {
}
