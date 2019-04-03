package com.isoft.rest.controller;

import java.util.List;
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

import com.isoft.rest.db.model.WeatherData;
import com.isoft.rest.db.repository.AdditionalRepository;
import com.isoft.rest.db.repository.ConfigRepository;
import com.isoft.rest.db.repository.WeatherRepository;
import com.isoft.rest.db.repository.WindRepository;
import com.isoft.rest.exception.ResourceNotFoundException;


@RestController
public class WeatherController {
    
    @Autowired(required = true)
    private WeatherRepository weather_repos;
    
    @GetMapping("/weather")
    public Page<WeatherData> getWeatherData(Pageable pageable) {
        return weather_repos.findAll(pageable);
    }
    
    @GetMapping("/weather_list")
    public List<WeatherData> getWeatherData() {
        return weather_repos.findAll();
    }
    
    @GetMapping("/weather/{weatherId}")
    public Optional<WeatherData> getWeatherData(@PathVariable Long weatherId) {
        return weather_repos.findById(weatherId);
    }
    
    @GetMapping("/weather/{weatherYear}")
    public List<WeatherData> getWeatherDataYear(@PathVariable Integer weatherYear) {
        return weather_repos.findWeatherByYear(weatherYear);
    }
    
    @GetMapping("/weather/{weatherYear}/{weatherMonth}")
    public List<WeatherData> getWeatherDataMonthYear(@PathVariable Integer weatherYear, @PathVariable String weatherMonth) {
        return weather_repos.findWeatherByMonthAndYear(weatherMonth, weatherYear);
    }
    
    @GetMapping("/weather/{weatherYear}/{weatherMonth}/{weatherDay}")
    public List<WeatherData> getWeatherDataMonthYear(@PathVariable Integer weatherYear, @PathVariable String weatherMonth, @PathVariable Integer weatherDay) {
        return weather_repos.findWeatherByDayAndMonthAndYear(weatherDay, weatherMonth, weatherYear);
    }
    
    @PostMapping("/weather")
    public WeatherData insertWeather(@Valid @RequestBody WeatherData weather) {
        return weather_repos.save(weather);
    }
    
    @PostMapping("/weather_test")
    public ResponseEntity<List<WeatherData>> insertWeatherList(List<WeatherData> w_list) {
        weather_repos.saveAll(w_list);
        return ResponseEntity.ok(w_list);
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
