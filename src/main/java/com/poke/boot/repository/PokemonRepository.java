package com.poke.boot.repository;

import com.poke.boot.model.Pokemon;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PokemonRepository extends PagingAndSortingRepository<Pokemon, Integer> {
    public Pokemon findByName(String name);
}
