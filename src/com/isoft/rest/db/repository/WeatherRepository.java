package com.isoft.rest.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isoft.rest.db.model.WeatherData;

public interface WeatherRepository extends JpaRepository<WeatherData, Long>{
    List<WeatherData> findWeatherById(Long weatherId);
    List<WeatherData> findWeatherByDayAndMonthAndYear(Integer day, String month, Integer year);
    List<WeatherData> findWeatherByMonthAndYear(String month, Integer year);
    List<WeatherData> findWeatherByYear(Integer year);
}
