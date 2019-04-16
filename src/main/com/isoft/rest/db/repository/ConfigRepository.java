package main.com.isoft.rest.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import main.com.isoft.rest.db.model.ConfigData;

public interface ConfigRepository extends JpaRepository<ConfigData, Long>{
    ConfigData findConfigById(Long configId);
    List<ConfigData> findConfigBySite(String site);
}
