package com.isoft.rest.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isoft.rest.db.model.Additional;

public interface AdditionalRepository extends JpaRepository<Additional, Long>{
    List<Additional> findAdditionalById(Long additionalId);
}
