package com.isoft.base.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isoft.base.db.model.ConfigData;

public interface ConfigRepository extends JpaRepository<ConfigData, Long>{
    List<ConfigData> findConfigById(Long configId);
}
