package br.com.municipios.entity.municipios.dto;

import lombok.Data;

@Data
public class MunicipioFilterParamsDTO {
    private String nome;
    private Boolean capital;
    private Long populacaoIni;
    private Long populacaoFim;
}
