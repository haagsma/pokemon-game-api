package com.poke.boot.dto;

import com.poke.boot.model.Pokemon;
import lombok.Data;

import java.util.List;

@Data

public class PokedexGoDTO {

    private List<Pokemon> pokemon;
    private List<Pokemon> results;
}
