package com.poke.boot.repository;

import com.poke.boot.model.Gym;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GymRepository extends PagingAndSortingRepository<Gym, Integer> {

}
