package com.poke.boot.repository;

import com.poke.boot.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends CrudRepository<Status, Integer> {
    public Status findByNemotecnicoIgnoreCaseContaining(String nemotecnico);
}
