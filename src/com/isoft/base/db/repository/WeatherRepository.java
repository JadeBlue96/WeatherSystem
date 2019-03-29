package com.isoft.base.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isoft.base.db.model.WeatherData;

public interface WeatherRepository extends JpaRepository<WeatherData, Long>{
    List<WeatherData> findWeatherById(Long weatherId);
}
