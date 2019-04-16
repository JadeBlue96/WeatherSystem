package main.com.isoft.rest.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import main.com.isoft.rest.db.model.Wind;

public interface WindRepository extends JpaRepository<Wind, Long>{
    Wind findWindById(Long windId);
}
