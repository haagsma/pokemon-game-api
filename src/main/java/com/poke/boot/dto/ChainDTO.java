package com.poke.boot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChainDTO {

    private GenericTypeDTO species;

    @JsonAlias("evolves_to")
    private List<EvolutionToDTO> evolvesTo;
}
