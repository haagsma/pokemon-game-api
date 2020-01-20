package com.poke.boot.repository;

import com.poke.boot.model.Move;
import com.poke.boot.model.PokemonMove;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MoveRepository extends PagingAndSortingRepository<Move, Integer> {
    public Move findByName(String name);
}
