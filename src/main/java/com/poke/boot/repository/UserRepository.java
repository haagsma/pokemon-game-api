package com.poke.boot.repository;

import com.poke.boot.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByEmailIgnoreCase(String email);
}
