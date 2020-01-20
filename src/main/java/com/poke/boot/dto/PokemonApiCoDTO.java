package com.poke.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonApiCoDTO implements Serializable {

    private Integer id;
    private Sprites sprites;
}
