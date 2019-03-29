package com.isoft.base.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isoft.base.db.model.WeatherData;
import com.isoft.base.db.repository.AdditionalRepository;
import com.isoft.base.db.repository.ConfigRepository;
import com.isoft.base.db.repository.WeatherRepository;
import com.isoft.base.db.repository.WindRepository;
import com.isoft.base.exception.ResourceNotFoundException;


@RestController
public class WeatherController {
    @Autowired
    private WeatherRepository weather_repos;
    
    @Autowired
    private AdditionalRepository add_repos;
    
    @Autowired
    private ConfigRepository config_repos;
    
    @Autowired
    private WindRepository wind_repos;
    
    @GetMapping("/weather")
    public Page<WeatherData> getWeatherData(Pageable pageable) {
        return weather_repos.findAll(pageable);
    }
    
    @GetMapping("/weather/{weatherId}")
    public Optional<WeatherData> getWeatherData(@PathVariable Long weatherId) {
        return weather_repos.findById(weatherId);
    }
    
    @PostMapping("/weather")
    public WeatherData addWeatherAdditional(@Valid @RequestBody WeatherData weather) {
        return weather_repos.save(weather);
    }
    
    @PutMapping("/weather/{weatherId}")
    public WeatherData updateWeatherData(@PathVariable Long weatherId,
                                   @Valid @RequestBody WeatherData weatherRequest) {
        
        return weather_repos.findById(weatherId)
                .map(weather -> {
                    weather.setTemp(weatherRequest.getTemp());
                    weather.setFeel_temp(weatherRequest.getFeel_temp());
                    weather.setStatus(weatherRequest.getStatus());
                    weather.setQuery_date();
                    return weather_repos.save(weather);
                }).orElseThrow(() -> new ResourceNotFoundException("WeatherData not found with id " + weatherId));
    }
    
    @DeleteMapping("/weather/{weatherId}")
    public ResponseEntity<?> deleteWeatherData(@PathVariable Long weatherId) {

        return weather_repos.findById(weatherId)
                .map(weather -> {
                    weather_repos.delete(weather);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("WeatherData not found with id " + weatherId));
    }
}
