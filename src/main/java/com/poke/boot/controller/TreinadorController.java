package com.poke.boot.controller;

import com.poke.boot.dto.CadastrarTreinadorDTO;
import com.poke.boot.dto.MoneyDTO;
import com.poke.boot.model.*;
import com.poke.boot.repository.*;
import com.poke.boot.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.Response;
import java.util.List;

@RequestMapping("/treinador")
@RestController
public class TreinadorController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    public PokemonService pokemonService;

    @Autowired
    private TreinadorRepository treinadorRepository;

    @Autowired
    private TreinadorPokemonRepository treinadorPokemonRepository;

    @Autowired
    private TreinadorItemRepository treinadorItemRepository;

    @Autowired
    private PokemonMoveRepository pokemonMoveRepository;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/{email}")
    public ResponseEntity<?> listPokedex(@PathVariable String email) {

        try {
            Treinador treinador = em.createQuery("Select t from Treinador t JOIN FETCH t.user u where u.email = :email", Treinador.class)
                    .setParameter("email", email)
                    .getSingleResult();

            return new ResponseEntity<>(treinador, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/team/{treinador}")
    public ResponseEntity<?> getTeam(@PathVariable Integer treinador) {
        List<TreinadorPokemon> treinadorPokemons = em.createQuery("SELECT tp FROM TreinadorPokemon tp WHERE tp.treinador.id = :id and inBag = true", TreinadorPokemon.class)
                .setParameter("id", treinador)
                .getResultList();
        return new ResponseEntity<>(treinadorPokemons, HttpStatus.OK);
    }

    @GetMapping("/nick/{nick}")
    public ResponseEntity<?> checkNick(@PathVariable String nick) {

        try {
            Treinador treinador = em.createQuery("Select t from Treinador t JOIN FETCH t.user u where t.nick = :nick", Treinador.class)
                    .setParameter("nick", nick)
                    .getSingleResult();

            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
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
        List<TreinadorPokemon> pokemons = em.createQuery("Select ti from TreinadorPokemon ti JOIN FETCH ti.treinador t WHERE t.id = :id order by ti.order", TreinadorPokemon.class)
                .setParameter("id", id)
                .getResultList();
        return new ResponseEntity<>(pokemons, HttpStatus.OK);
    }

    @PostMapping("/attexp")
    public ResponseEntity<?> updateExp(@RequestBody Treinador treinador) {
        Treinador trainer = treinadorRepository.findById(treinador.getId()).get();
        trainer.setLevel(treinador.getLevel());
        trainer.setExp(treinador.getExp());
        treinadorRepository.save(trainer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/catched")
    public ResponseEntity<?> catched(@RequestBody TreinadorPokemon treinadorPokemon) {
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
        treinadorPokemon = treinadorPokemonRepository.save(treinadorPokemon);
        return new ResponseEntity<>(treinadorPokemon, HttpStatus.OK);
    }

    @PostMapping("/useitem")
    public ResponseEntity<?> useItem(@RequestBody TreinadorItem treinadorItem) {
        TreinadorItem item = treinadorItemRepository.save(treinadorItem);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @PostMapping("/getmoney")
    public ResponseEntity<?> getMoney(@RequestBody MoneyDTO moneyDTO) {
        Treinador treinador = treinadorRepository.findById(moneyDTO.getId()).get();
        treinador.setAmount(treinador.getAmount() + moneyDTO.getMoney());
        treinadorRepository.save(treinador);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarTreinador(@RequestBody CadastrarTreinadorDTO cadastrarTreinadorDTO) {
        User user = userRepository.findByEmailIgnoreCase(cadastrarTreinadorDTO.getEmail());
        Status status = statusRepository.findByNemotecnicoIgnoreCaseContaining("ATIVO");
        cadastrarTreinadorDTO.getTreinador().setStatus(status);
        cadastrarTreinadorDTO.getTreinador().setUser(user);
        Treinador treinador = treinadorRepository.save(cadastrarTreinadorDTO.getTreinador());
        return new ResponseEntity<>(treinador, HttpStatus.OK);
    }
    @PostMapping("/pokemon-inicial")
    public ResponseEntity<?> pokemonInicial(@RequestBody TreinadorPokemon treinadorPokemon) {
        List<PokemonMove> pokemonMoves = em.createQuery("SELECT pm FROM PokemonMove pm WHERE pm.pokemon = :pokemon AND pm.level = 1", PokemonMove.class)
                .setParameter("pokemon", treinadorPokemon.getPokemon())
                .getResultList();

        treinadorPokemon.setMove1(pokemonMoves.get(0).getMove());
        if (pokemonMoves.size() > 1) treinadorPokemon.setMove2(pokemonMoves.get(1).getMove());
        if (pokemonMoves.size() > 2) treinadorPokemon.setMove3(pokemonMoves.get(2).getMove());
        if (pokemonMoves.size() > 3) treinadorPokemon.setMove4(pokemonMoves.get(3).getMove());

        treinadorPokemon.setAttack(43);
        treinadorPokemon.setSpecialAttack(43);
        treinadorPokemon.setDefense(37);
        treinadorPokemon.setSpecialDefense(37);
        treinadorPokemon.setSpeed(23);
        treinadorPokemon.setHp(55);
        treinadorPokemon.setLevel(1);
        treinadorPokemon.setExp(0);
        treinadorPokemon.setOrder(1);
        treinadorPokemon.setInBag(true);

        treinadorPokemonRepository.save(treinadorPokemon);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
