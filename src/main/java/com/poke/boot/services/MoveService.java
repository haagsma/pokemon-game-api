package com.poke.boot.services;

import com.poke.boot.dto.GenericTypeDTO;
import com.poke.boot.dto.PokemonDTO;
import com.poke.boot.dto.PokemonTypeDTO;
import com.poke.boot.model.Move;
import com.poke.boot.model.Pokemon;
import com.poke.boot.model.PokemonType;
import com.poke.boot.model.RelPokemonType;
import com.poke.boot.repository.MoveRepository;
import com.poke.boot.repository.PokemonRepository;
import com.poke.boot.repository.PokemonTypeRepository;
import com.poke.boot.repository.RelPokemonTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoveService {

    @Autowired
    private PokemonTypeRepository pokemonTypeRepository;

    @Autowired
    private MoveRepository moveRepository;

    public boolean saveMoveFromApi(Move move) {
        PokemonType type = pokemonTypeRepository.findByName(move.getType().getName());
        if (type == null) {
            type = pokemonTypeRepository.save(move.getType());
        }
        move.setType(type);
        moveRepository.save(move);
        return true;
    }

}
