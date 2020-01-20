package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "pokemon_map")
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown =  true)
public class PokemonMap implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float lat;
    private Float lng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon")
    private Pokemon pokemon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "move1")
    private Move move1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "move2")
    private Move move2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "move3")
    private Move move3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "move4")
    private Move move4;
}
