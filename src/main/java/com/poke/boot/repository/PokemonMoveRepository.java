package com.poke.boot.repository;

import com.poke.boot.model.Move;
import com.poke.boot.model.PokemonMove;
import com.poke.boot.model.PokemonType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PokemonMoveRepository extends PagingAndSortingRepository<PokemonMove, Integer> {

}
