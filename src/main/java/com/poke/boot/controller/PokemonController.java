package com.poke.boot.controller;

import com.poke.boot.model.*;
import com.poke.boot.repository.EvolutionRepository;
import com.poke.boot.repository.TreinadorPokemonRepository;
import com.poke.boot.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/pokemon")
@RestController
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private TreinadorPokemonRepository treinadorPokemonRepository;

    @Autowired
    private EvolutionRepository evolutionRepository;

    @GetMapping("/pokedex")
    public ResponseEntity<?> listPokedex(Pageable pageable) {
        return new ResponseEntity<Iterable<Pokemon>>(pokemonService.pokemonList(pageable), HttpStatus.OK);
    }

    @PersistenceContext
    private EntityManager em;

    @PostMapping("/att")
    public ResponseEntity<?> attStatus(@RequestBody TreinadorPokemon treinadorPokemon) {

        TreinadorPokemon pokemon = treinadorPokemonRepository.findById(treinadorPokemon.getId()).get();
        pokemon.setAttack(treinadorPokemon.getAttack());
        pokemon.setSpecialAttack(treinadorPokemon.getSpecialAttack());
        pokemon.setDefense(treinadorPokemon.getDefense());
        pokemon.setSpecialDefense(treinadorPokemon.getSpecialDefense());
        pokemon.setSpeed(treinadorPokemon.getSpeed());
        pokemon.setHp(treinadorPokemon.getHp());
        pokemon.setLevel(treinadorPokemon.getLevel());
        pokemon.setExp(treinadorPokemon.getExp());
        pokemon.setPokemon(treinadorPokemon.getPokemon());
        pokemon.setMove1(treinadorPokemon.getMove1());
        pokemon.setMove2(treinadorPokemon.getMove2());
        pokemon.setMove3(treinadorPokemon.getMove3());
        pokemon.setMove4(treinadorPokemon.getMove4());
        treinadorPokemonRepository.save(pokemon);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody TreinadorPokemon treinadorPokemon) {
        treinadorPokemonRepository.save(treinadorPokemon);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/check-evolution")
    public ResponseEntity<?> checkEvolution(@RequestBody TreinadorPokemon treinadorPokemon) {
        List<Evolution> evolutions = em.createQuery("SELECT e FROM Evolution e WHERE e.pokemon = :pokemon AND e.minLevel is not null ", Evolution.class)
                .setParameter("pokemon", treinadorPokemon.getPokemon())
                .getResultList();
        Evolution evolution = null;
        if (evolutions.size() > 0) {
            evolution  = evolutions.get(0);
        }
        return new ResponseEntity<>(evolution, HttpStatus.OK);
    }

    @PostMapping("/check-evolution-stone")
    public ResponseEntity<?> checkEvolutionByStone(@RequestBody Item item) {
        List<Evolution> evolutions = evolutionRepository.findByStone(item);
        return new ResponseEntity<>(evolutions, HttpStatus.OK);
    }

    @PostMapping("/check-attack")
    public ResponseEntity<?> checkAttack(@RequestBody TreinadorPokemon treinadorPokemon) {
        List<PokemonMove> pokemonMoves = em.createQuery("SELECT pm FROM PokemonMove pm WHERE pm.pokemon = :pokemon AND pm.level = :level", PokemonMove.class)
                .setParameter("level", treinadorPokemon.getLevel())
                .setParameter("pokemon", treinadorPokemon.getPokemon())
                .getResultList();
        return new ResponseEntity<>(pokemonMoves, HttpStatus.OK);
    }

    @GetMapping("/abandonar/{id}")
    @Transactional
    public ResponseEntity<?> abandonar(@PathVariable Integer id) {
        treinadorPokemonRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/iniciais")
    public ResponseEntity<?> getIniciais() {
        List<String> iniciais = Arrays.asList(
                "Bulbasaur", "Charmander", "Squirtle",
                "Chikorita", "Totodile", "Cyndaquil",
                "Treecko", "Torchic", "Mudkip",
                "Turtwig", "Chimchar", "Piplup",
                "Snivy", "Tepig", "Oshawott",
                "Chespin", "Fennekin", "Froakie",
                "Rowlet", "Litten", "Popplio"
                );
        List<Pokemon> pokemons = em.createQuery("SELECT p FROM Pokemon p WHERE p.name in (:lista)", Pokemon.class)
                .setParameter("lista", iniciais)
                .getResultList();
        return new ResponseEntity<>(pokemons, HttpStatus.OK);
    }

    @GetMapping("/gift")
    public ResponseEntity<?> getPokemonGift() {
        Pokemon pokemon = pokemonService.getPokemonGiftRandom();
        TreinadorPokemon treinadorPokemon = new TreinadorPokemon();
        treinadorPokemon.setPokemon(pokemon);
        treinadorPokemon.setLevel(50);
        List<PokemonMove> pokemonMove = em.createQuery("SELECT pm FROM PokemonMove pm WHERE pm.pokemon = :pokemon AND pm.level <= :level ORDER BY pm.level DESC", PokemonMove.class)
                .setParameter("pokemon", treinadorPokemon.getPokemon())
                .setParameter("level", treinadorPokemon.getLevel())
                .getResultList();

        treinadorPokemon.setMove1(pokemonMove.get(0).getMove());

        if (pokemonMove.size() > 1) {
            treinadorPokemon.setMove2(pokemonMove.get(1).getMove());
        } else {
            treinadorPokemon.setMove2(null);
        }
        if (pokemonMove.size() > 2) {
            treinadorPokemon.setMove3(pokemonMove.get(2).getMove());
        } else {
            treinadorPokemon.setMove3(null);
        }
        if (pokemonMove.size() > 3) {
            treinadorPokemon.setMove4(pokemonMove.get(3).getMove());
        } else {
            treinadorPokemon.setMove4(null);
        }
        return new ResponseEntity<>(treinadorPokemon, HttpStatus.OK);
    }

}
