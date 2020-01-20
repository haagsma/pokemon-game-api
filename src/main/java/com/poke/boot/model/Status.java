package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Status implements Serializable {

    /**
     * id	int(11)
     * nemotecnico	varchar(40)
     * descricao	varchar(255)
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nemotecnico;
    private String descricao;

}
