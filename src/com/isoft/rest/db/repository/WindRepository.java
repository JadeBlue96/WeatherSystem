package com.isoft.rest.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isoft.rest.db.model.Wind;

public interface WindRepository extends JpaRepository<Wind, Long>{
    List<Wind> findWindById(Long windId);
}
