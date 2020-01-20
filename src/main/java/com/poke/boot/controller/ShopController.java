package com.poke.boot.controller;

import com.poke.boot.model.Evolution;
import com.poke.boot.model.Pokemon;
import com.poke.boot.model.PokemonMove;
import com.poke.boot.model.TreinadorPokemon;
import com.poke.boot.repository.EvolutionRepository;
import com.poke.boot.repository.ShopRepository;
import com.poke.boot.repository.TreinadorPokemonRepository;
import com.poke.boot.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequestMapping("/shop")
@RestController
public class ShopController {

    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
       return new ResponseEntity<>(shopRepository.findAll(), HttpStatus.OK);
   }
}
