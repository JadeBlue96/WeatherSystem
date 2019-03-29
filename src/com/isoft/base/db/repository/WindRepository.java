package com.isoft.base.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.isoft.base.db.model.Wind;

public interface WindRepository extends JpaRepository<Wind, Long>{
    List<Wind> findWindById(Long windId);
}
