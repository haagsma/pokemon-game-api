package com.poke.boot.repository;

import com.poke.boot.model.Pokemon;
import com.poke.boot.model.PokemonType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PokemonTypeRepository extends PagingAndSortingRepository<PokemonType, Integer> {
    public PokemonType findByName(String name);
}
