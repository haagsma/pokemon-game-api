package com.poke.boot.services;

import com.poke.boot.model.Evolution;
import com.poke.boot.model.Pokemon;
import com.poke.boot.model.PokemonMap;
import com.poke.boot.model.PokemonMove;
import com.poke.boot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class PokemonMapService {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokemonTypeRepository pokemonTypeRepository;

    @Autowired
    private RelPokemonTypeRepository relPokemonTypeRepository;

    @Autowired
    private PokemonMoveRepository pokemonMoveRepository;

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private EvolutionRepository evolutionRepository;

    @Autowired
    private PokemonMapRepository pokemonMapRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void fillMap() {
        int delete = em.createQuery("DELETE FROM PokemonMap WHERE id > 0").executeUpdate();
        BigInteger count = (BigInteger) em.createNativeQuery("select count(*) from evolution").getSingleResult();
        List<Evolution> pokemons = em.createQuery("SELECT e FROM Evolution e WHERE id in (:p1, :p2, :p3, :p4, :p5, :p6, :p7, :p8, :p9, :p10, :p11, :p12, :p13, :p14, :p15, :p16, :p17, :p18, :p19, :p20)", Evolution.class)
                .setParameter("p1", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p2", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p3", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p4", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p5", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p6", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p7", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p8", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p9", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p10", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p11", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p12", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p13", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p14", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p15", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p16", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p17", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p18", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p19", 1 + (int) (Math.random() * count.intValue()))
                .setParameter("p20", 1 + (int) (Math.random() * count.intValue()))
                .getResultList();
        for (Evolution evolution: pokemons) {
            List<PokemonMove> pokemonMoves = em.createQuery("SELECT pm FROM PokemonMove pm " +
                                                               "JOIN FETCH pm.move m " +
                                                               "JOIN FETCH pm.pokemon p " +
                                                               "WHERE m.power is not null " +
                                                               "AND p.id = :pokemon " +
                                                               "AND pm.level is not null " +
                                                               "ORDER BY m.power", PokemonMove.class)
                    .setParameter("pokemon", evolution.getPokemon().getId())
                    .setMaxResults(4)
                    .getResultList();
            if (pokemonMoves.size() < 1) {
                System.out.println("Não tem move: " + evolution.getPokemon().getName());
                continue;
            }
            PokemonMap pokemon = new PokemonMap();
            pokemon.setLat((float) 0.00);
            pokemon.setLng((float) 0.00);
            pokemon.setPokemon(evolution.getPokemon());
            pokemon.setMove1(pokemonMoves.get(0).getMove());

            if (pokemonMoves.size() > 1)
            pokemon.setMove2(pokemonMoves.get(1).getMove());

            if (pokemonMoves.size() > 2)
            pokemon.setMove3(pokemonMoves.get(2).getMove());

            if (pokemonMoves.size() > 3)
            pokemon.setMove4(pokemonMoves.get(3).getMove());

            pokemonMapRepository.save(pokemon);
        }
    }

}
