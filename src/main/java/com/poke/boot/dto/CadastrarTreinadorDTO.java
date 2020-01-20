package com.poke.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poke.boot.model.Treinador;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CadastrarTreinadorDTO {
    private String email;
    private Treinador treinador;
}
