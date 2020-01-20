package com.poke.boot.repository;

import com.poke.boot.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    public Category findByName(String name);
}
