package com.poke.boot.services;

import com.poke.boot.dto.ChainDTO;
import com.poke.boot.dto.EvolutionToDTO;
import com.poke.boot.dto.PokemonDTO;
import com.poke.boot.dto.PokemonTypeDTO;
import com.poke.boot.model.*;
import com.poke.boot.repository.*;
import net.bytebuddy.asm.MemberSubstitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PokemonService {

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
    private ItemRepository itemRepository;

    @Autowired
    private EvolutionRepository evolutionRepository;

    @PersistenceContext
    private EntityManager em;

    public Iterable<Pokemon> pokemonList(Pageable pageable) {
        return pokemonRepository.findAll(pageable);
    }

    public Pokemon getPokemonGiftRandom() {
        return (Pokemon) em.createNativeQuery("select * from pokemon ORDER BY RAND() LIMIT 1", Pokemon.class).getSingleResult();
    }

    public boolean saveFromApi(PokemonDTO pokemonDTO) {
        List<PokemonType> types = new ArrayList<>();
        for (PokemonTypeDTO pokemonTypeDTO: pokemonDTO.getTypes()) {
            PokemonType pokemonType = pokemonTypeRepository.findByName(pokemonTypeDTO.getType().getName());
            if (pokemonType == null) {
                pokemonType = pokemonTypeRepository.save(pokemonTypeDTO.getType());
            }
            types.add(pokemonType);
        }
        Pokemon pokemon = new Pokemon();
        pokemon.setId(pokemonDTO.getId());
        pokemon.setNum(transformToPokeNumber(pokemonDTO.getId()));
        pokemon.setName(pokemonDTO.getName());
        pokemon.setHeight(pokemonDTO.getHeight());
        pokemon.setWeight(pokemonDTO.getWeight());
        pokemon = pokemonRepository.save(pokemon);

        for (PokemonType type: types) {
            RelPokemonType rel = new RelPokemonType();
            rel.setPokemon(pokemon);
            rel.setType(type);
            relPokemonTypeRepository.save(rel);
        }
        return true;
    }

    public boolean saveMoveOfPokemon(Pokemon pokemon, PokemonDTO pokemonDTO) {

        for (PokemonDTO moveApi: pokemonDTO.getMoves()) {
//            if (checkMove(moveApi.getMove().getName())) {
                Move move = moveRepository.findByName(moveApi.getMove().getName());
                if (move != null) {
                    PokemonMove pokemonMove = new PokemonMove();
                    pokemonMove.setMove(move);
                    pokemonMove.setPokemon(pokemon);
                    if (moveApi.getDetails().get(0).getLevelLearn() > 0) pokemonMove.setLevel(moveApi.getDetails().get(0).getLevelLearn());
                    pokemonMoveRepository.save(pokemonMove);
                } else {
                    System.out.println("Move não encontrado: " + moveApi.getMove().getName());
                }
//            }
        }

        return true;
    }
    private boolean checkMove(String name) {
        if (
                name.equals("comet-punch") ||
                name.equals("cut") ||
                name.equals("double-slap") ||
                name.equals("fire-punch") ||
                name.equals("guillotine") ||
                name.equals("ice-punch") ||
                name.equals("karate-chop") ||
                name.equals("mega-punch") ||
                name.equals("pay-day") ||
                name.equals("razor-wind") ||
                name.equals("scratch") ||
                name.equals("thunder-punch") ||
                name.equals("vice-grip")
        ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean saveLevel(ChainDTO chainDTO) {

        Pokemon pokemon = pokemonRepository.findByName(chainDTO.getSpecies().getName());
        if (pokemon != null) {
            for (EvolutionToDTO evolutionToDTO: chainDTO.getEvolvesTo()) {
                try {
                    Pokemon pokemonEvolution = pokemonRepository.findByName(evolutionToDTO.getSpecies().getName());
                    Evolution evolution = new Evolution();
                    evolution.setPokemon(pokemon);
                    evolution.setTarget(pokemonEvolution);
                    if (evolutionToDTO.getEvolutionDetail().get(0).getMinLevel() == null && evolutionToDTO.getEvolutionDetail().get(0).getItem() != null) {
                        Item stone = itemRepository.findByNemotecnico(evolutionToDTO.getEvolutionDetail().get(0).getItem().getName());
                        evolution.setStone(stone);
                    } else {
                        evolution.setMinLevel(evolutionToDTO.getEvolutionDetail().get(0).getMinLevel());
                    }
                    evolutionRepository.save(evolution);
                    if (evolutionToDTO.getEvolvesTo().size() > 0) {
                        for (EvolutionToDTO evolutionToDTO2: evolutionToDTO.getEvolvesTo()) {
                            Pokemon pokemonEvolution2 = pokemonRepository.findByName(evolutionToDTO2.getSpecies().getName());
                            Evolution evolution2 = new Evolution();
                            evolution2.setPokemon(pokemonEvolution);
                            evolution2.setTarget(pokemonEvolution2);
                            if (evolutionToDTO2.getEvolutionDetail().get(0).getMinLevel() == null && evolutionToDTO2.getEvolutionDetail().get(0).getItem() != null) {
                                Item stone2 = itemRepository.findByNemotecnico(evolutionToDTO2.getEvolutionDetail().get(0).getItem().getName());
                                evolution2.setStone(stone2);
                            } else {
                                evolution2.setMinLevel(evolutionToDTO2.getEvolutionDetail().get(0).getMinLevel());
                            }
                            evolutionRepository.save(evolution2);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Falha ao salvar evolução do: " + chainDTO.getSpecies().getName());
                }
            }
        } else {
            System.out.println("Pokemon não cadastrado: " + chainDTO.getSpecies().getName());
        }
        return true;
    }

    private String transformToPokeNumber(Integer id) {
        String idStr = id.toString();
        if (idStr.length() == 1) {
            idStr = "00" + idStr;
        } else if (idStr.length() == 2) {
            idStr = "0" + idStr;
        }
        return idStr;
    }

}
