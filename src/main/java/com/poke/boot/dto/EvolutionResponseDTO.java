package com.poke.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EvolutionResponseDTO {

    private ChainDTO chain;
}
