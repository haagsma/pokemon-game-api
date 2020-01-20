package com.poke.boot.repository;

import com.poke.boot.model.Evolution;
import com.poke.boot.model.TreinadorPokemon;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TreinadorPokemonRepository extends PagingAndSortingRepository<TreinadorPokemon, Integer> {

}
