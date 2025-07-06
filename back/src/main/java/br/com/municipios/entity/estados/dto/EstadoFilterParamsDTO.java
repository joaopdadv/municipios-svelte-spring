package br.com.municipios.entity.estados.dto;

import lombok.Data;

@Data
public class EstadoFilterParamsDTO {
    private String uf;
    private Long populacaoIni;
    private Long populacaoFim;
}
