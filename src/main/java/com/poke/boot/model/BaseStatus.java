package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "base_status")
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown =  true)
public class BaseStatus implements Serializable {

    /**
     * id	int(11)
     * nemotecnico	varchar(100)
     * descricao	varchar(255)
     * attack	int(11)
     * special_attack	int(11)
     * defense	int(11)
     * special_defense	int(11)
     * speed	int(11)
     * hp	int(11)
     * created_at	timestamp
     * updated_at	timestamp
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nemotecnico;
    private String descricao;
    private Integer attack;

    @Column(name = "special_attack")
    private Integer specialAttack;
    private Integer defense;

    @Column(name = "special_defense")
    private Integer specialDefense;
    private Integer speed;
    private Integer hp;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
