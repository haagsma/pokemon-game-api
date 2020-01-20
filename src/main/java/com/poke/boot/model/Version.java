package com.poke.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "poke_version")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Version implements Serializable {

    /**
     * id	int(11)
     * version	int(11)
     * date_version	timestamp
     */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer version;
    private String url;

    @Column(name = "date_version")
    private Date date;
}
