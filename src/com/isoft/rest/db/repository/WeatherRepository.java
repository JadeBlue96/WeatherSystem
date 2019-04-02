package com.isoft.rest.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isoft.rest.db.model.WeatherData;

public interface WeatherRepository extends JpaRepository<WeatherData, Long>{
    List<WeatherData> findWeatherById(Long weatherId);
}
