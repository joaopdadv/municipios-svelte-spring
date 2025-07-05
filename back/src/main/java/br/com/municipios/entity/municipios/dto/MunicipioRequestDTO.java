package br.com.municipios.entity.municipios.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunicipioRequestDTO {
    private String nome;
    private String codigo;
    private Long populacao;
    private boolean isCapital;
}
