package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "treinador_item")
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown =  true)
public class TreinadorItem implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treinador")
    private Treinador treinador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tm")
    private Move tm;
}
