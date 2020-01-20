package com.poke.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poke.boot.model.PokemonType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonTypeDTO implements Serializable {

    private Integer slot;
    private PokemonType type;


}
