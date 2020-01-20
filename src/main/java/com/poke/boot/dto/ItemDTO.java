package com.poke.boot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poke.boot.model.Category;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO {

    private Integer id;
    private String name;
    private Integer cost;
    private Category category;

    @JsonAlias("effect_entries")
    private List<GenericTypeDTO> effectEntries;
    private List<GenericTypeDTO> names;
}
