package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "item")
@Data
@JsonIgnoreProperties(ignoreUnknown =  true)
public class Item implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nemotecnico;
    private String name;
    private String description;
    private Integer cost;
    private Float effect;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;
}
