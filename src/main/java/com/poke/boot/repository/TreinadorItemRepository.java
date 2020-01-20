package com.poke.boot.repository;

import com.poke.boot.model.Treinador;
import com.poke.boot.model.TreinadorItem;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TreinadorItemRepository extends PagingAndSortingRepository<TreinadorItem, Integer> {
}
