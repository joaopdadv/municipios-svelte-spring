package br.com.municipios.entity.estados.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoRequestDTO {
    private String uf;

    private Integer codigoUf;
}