package com.poke.boot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericTypeDTO {

    private Integer id;
    private String name;
    private String url;
    private GenericTypeDTO language;
    private String effect;
    private ItemDTO item;

    @JsonAlias("min_level")
    private Integer minLevel;

    @JsonAlias("level_learned_at")
    private Integer levelLearn;
}
