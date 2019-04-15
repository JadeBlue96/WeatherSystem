package com.isoft.rest.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.isoft.rest.db.model.Additional;
import com.isoft.rest.db.repository.AdditionalRepository;
import com.isoft.rest.exception.ResourceNotFoundException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AdditionalController {
    @Autowired
    private AdditionalRepository additional_repos;
    
    @GetMapping("/additional")
    public Page<Additional> getAdditionalData(Pageable pageable) {
        return additional_repos.findAll(pageable);
    }
    
    @GetMapping("/additional/{additionalId}")
    public Optional<Additional> getAdditionalData(@PathVariable Long additionalId) {
        return additional_repos.findById(additionalId);
    }
    
    @PostMapping("/additional")
    public Additional createAdditional(@Valid @RequestBody Additional additional) {
        return additional_repos.save(additional);
    }
    
    @PutMapping("/additional/{additionalId}")
    public Additional updateAdditional(@PathVariable Long additionalId,
                                   @Valid @RequestBody Additional additionalRequest) {
        return additional_repos.findById(additionalId)
                .map(additional -> {
                    additional.setHumidity(additionalRequest.getHumidity());
                    additional.setVisibility(additionalRequest.getVisibility());
                    additional.setPressure(additionalRequest.getPressure());
                    return additional_repos.save(additional);
                }).orElseThrow(() -> new ResourceNotFoundException("Additional not found with id " + additionalId));
    }
    
    @DeleteMapping("/additional/{additionalId}")
    public ResponseEntity<?> deleteAdditional(@PathVariable Long additionalId) {
        return additional_repos.findById(additionalId)
                .map(additional -> {
                    additional_repos.delete(additional);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Additional not found with id " + additionalId));
    }
}
