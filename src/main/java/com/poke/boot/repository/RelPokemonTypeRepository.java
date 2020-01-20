package com.poke.boot.repository;

import com.poke.boot.model.Pokemon;
import com.poke.boot.model.RelPokemonType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RelPokemonTypeRepository extends PagingAndSortingRepository<RelPokemonType, Integer> {
}
