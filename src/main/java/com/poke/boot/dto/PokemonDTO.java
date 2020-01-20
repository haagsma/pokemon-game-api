package com.poke.boot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown =  true)
public class PokemonDTO implements Serializable {

    private Integer id;
    private String num;
    private String name;
    private String img;
    private Integer height;
    private Integer weight;
    private List<PokemonTypeDTO> types;
    private String url;
    private List<PokemonDTO> moves;
    private GenericTypeDTO move;

    @JsonAlias("version_group_details")
    private List<GenericTypeDTO> details;
}
