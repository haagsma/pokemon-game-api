package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "evolution")
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown =  true)
public class Evolution implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon")
    private Pokemon pokemon;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target")
    private Pokemon target;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stone")
    private Item stone;

    @Column(name = "min_level")
    private Integer minLevel;

}
