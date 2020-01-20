package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "gym")
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown =  true)
public class Gym implements Serializable {

    /**
     id	int(11)
     nemotecnico	varchar(100)
     name	varchar(255)
     treinador	int(11)
     badge	varchar(100)
     gen	varchar(50)
     active	int(11)
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nemotecnico;
    private String badge;
    private String name;
    private String gen;
    private Boolean active;
    private Double lat;
    private Double lng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treinador")
    private Treinador treinador;

}
