package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "treinador_pokemon")
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown =  true)
public class TreinadorPokemon implements Serializable {

    /**
     * id	int(11)
     * treinador	int(11)
     * pokemon	int(11)
     * attack	int(11)
     * special_attack	int(11)
     * defense	int(11)
     * special_defense	int(11)
     * speed	int(11)
     * hp	int(11)
     * in_bag	int(11)
     * created_at	timestamp
     * updated_at	timestamp
     * move1	int(11)
     * move2	int(11)
     * move3	int(11)
     * move4	int(11)
     * level	int(11)
     * order	int(11)
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treinador")
    private Treinador treinador;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon")
    private Pokemon pokemon;
    private Integer attack;

    @Column(name = "special_attack")
    private Integer specialAttack;

    private Integer defense;

    @Column(name = "special_defense")
    private Integer specialDefense;
    private Integer speed;
    private Integer hp;
    private Integer level;
    private Integer exp;

    @Column(name = "`order`")
    private Integer order;

    @Column(name = "in_bag")
    private Boolean inBag;


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


    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
