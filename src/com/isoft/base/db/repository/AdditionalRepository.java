package com.isoft.base.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isoft.base.db.model.Additional;

public interface AdditionalRepository extends JpaRepository<Additional, Long>{
    List<Additional> findAdditionalById(Long additionalId);
}
