package com.poke.boot.repository;

import com.poke.boot.model.Item;
import com.poke.boot.model.Treinador;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TreinadorRepository extends PagingAndSortingRepository<Treinador, Integer> {

    public Treinador findByNick(String nick);
}
