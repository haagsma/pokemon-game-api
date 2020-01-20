package com.poke.boot.controller;

import com.poke.boot.model.PokemonMap;
import com.poke.boot.model.Treinador;
import com.poke.boot.model.TreinadorItem;
import com.poke.boot.model.TreinadorPokemon;
import com.poke.boot.services.PokemonMapService;
import com.poke.boot.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Timer;

@RequestMapping("/mapa")
@RestController
public class MapaController {

    @Autowired
    public PokemonService pokemonService;

    @Autowired
    private PokemonMapService pokemonMapService;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("")
    public ResponseEntity<?> listPokemons() {

        List<PokemonMap> pokemons = em.createQuery("Select t from PokemonMap t ", PokemonMap.class)
                .getResultList();

        return new ResponseEntity<>(pokemons, HttpStatus.OK);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<?> getItems(@PathVariable Integer id) {
        List<TreinadorItem> items = em.createQuery("Select ti from TreinadorItem ti JOIN FETCH ti.treinador t WHERE t.id = :id", TreinadorItem.class)
                .setParameter("id", id)
                .getResultList();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    @GetMapping("/pokemons/{id}")
    public ResponseEntity<?> getPokemons(@PathVariable Integer id) {
        List<TreinadorPokemon> pokemons = em.createQuery("Select ti from TreinadorPokemon ti JOIN FETCH ti.treinador t WHERE t.id = :id", TreinadorPokemon.class)
                .setParameter("id", id)
                .getResultList();
        return new ResponseEntity<>(pokemons, HttpStatus.OK);
    }

    @GetMapping("/fillmap")
    public ResponseEntity<?> fillMap() {
        new Thread(() -> {
            while (true) {
                try {
                    pokemonMapService.fillMap();
                    Thread.sleep(120000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
