package com.poke.boot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties( ignoreUnknown = true)
public class Sprites implements Serializable {

    @JsonAlias(value = "front_default")
    private String frontDefault;
}
