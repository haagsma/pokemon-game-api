package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "move")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Move implements Serializable {

    /**
     * id	int(11)
     * accuracy	int(11)
     * name	varchar(100)
     * power	int(11)
     * pp	int(11)
     * priority	int(11)
     * type	int(11)
     * paralize
     * poison
     * burn
     * sleep
     * freeze
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer accuracy;
    private String name;
    private Integer power;
    private Integer pp;
    private Integer priority;
    private Integer heal;
    private Boolean paralize;
    private Boolean poison;
    private Boolean burn;
    private Boolean sleep;
    private Boolean freeze;

    @ManyToOne
    @JoinColumn(name = "type")
    private PokemonType type;
}
