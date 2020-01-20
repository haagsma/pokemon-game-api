package com.poke.boot.repository;

import com.poke.boot.model.Evolution;
import com.poke.boot.model.Item;
import com.poke.boot.model.Pokemon;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EvolutionRepository extends PagingAndSortingRepository<Evolution, Integer> {

    public List<Evolution> findByPokemon(Pokemon pokemon);
    Evolution findByPokemonAndStone(Pokemon pokemon, Item stone);
    List<Evolution> findByStone(Item stone);

}
