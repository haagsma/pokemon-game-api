package com.poke.boot.repository;

import com.poke.boot.model.PokemonMap;
import com.poke.boot.model.PokemonMove;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PokemonMapRepository extends PagingAndSortingRepository<PokemonMap, Integer> {

}
