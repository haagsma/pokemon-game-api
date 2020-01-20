package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "treinador")
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown =  true)
public class Treinador implements Serializable {

    /**
     * id	int(11)
     * user	int(11)
     * nick	varchar(100)
     * status	int(11)
     * created_at	timestamp
     * updated_at	timestamp
     * level	int(11)
     * exp	int(11)
     * amount	int(11)
     * avatar	int(11)
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;
    private String nick;
    private Integer level;
    private Integer exp;
    private Integer amount;
    private Integer avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private Status status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
