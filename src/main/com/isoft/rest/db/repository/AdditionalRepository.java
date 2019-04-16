package main.com.isoft.rest.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import main.com.isoft.rest.db.model.Additional;

public interface AdditionalRepository extends JpaRepository<Additional, Long>{
    Additional findAdditionalById(Long additionalId);
}
