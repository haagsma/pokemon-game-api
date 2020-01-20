package com.poke.boot.repository;

import com.poke.boot.model.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {

    public Item findByNemotecnico(String nemotecnico);
}
