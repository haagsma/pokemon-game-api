package com.poke.boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatGlobalDTO {
    private Integer treinador;
    private Integer avatar;
    private String sender;
    private String message;
}
