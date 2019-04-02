package com.isoft.rest.controller;

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

import com.isoft.rest.db.model.Wind;
import com.isoft.rest.db.repository.WindRepository;
import com.isoft.rest.exception.ResourceNotFoundException;


@RestController
public class WindController {
    
    @Autowired
    private WindRepository wind_repos;
    
    @GetMapping("/wind")
    public Page<Wind> getWindData(Pageable pageable) {
        return wind_repos.findAll(pageable);
    }
    
    @GetMapping("/wind/{windId}")
    public Optional<Wind> getWindData(@PathVariable Long windId) {
        return wind_repos.findById(windId);
    }
    
    @PostMapping("/wind")
    public Wind createWind(@Valid @RequestBody Wind wind) {
        return wind_repos.save(wind);
    }
    
    @PutMapping("/wind/{windId}")
    public Wind updateWind(@PathVariable Long windId,
                                   @Valid @RequestBody Wind windRequest) {
        return wind_repos.findById(windId)
                .map(wind -> {
                    wind.setWind_direction(windRequest.getWind_direction());
                    wind.setWind_spd(windRequest.getWind_spd());
                    wind.setWind_status(windRequest.getWind_status());
                    return wind_repos.save(wind);
                }).orElseThrow(() -> new ResourceNotFoundException("Wind not found with id " + windId));
    }
    
    @DeleteMapping("/wind/{windId}")
    public ResponseEntity<?> deleteWind(@PathVariable Long windId) {
        return wind_repos.findById(windId)
                .map(wind -> {
                    wind_repos.delete(wind);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Wind not found with id " + windId));
    }
}
