package com.poke.boot.repository;

import com.poke.boot.model.Evolution;
import com.poke.boot.model.Pokemon;
import com.poke.boot.model.Shop;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ShopRepository extends PagingAndSortingRepository<Shop, Integer> {

    @Override
    public List<Shop> findAll();

}
