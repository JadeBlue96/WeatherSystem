package com.isoft.rest.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isoft.rest.db.model.WeatherUser;

public interface UserRepository extends JpaRepository<WeatherUser, String>{
    
}
