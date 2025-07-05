package br.com.municipios.entity.municipios.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MunicipioResponseDTO {
    private Long id;
    private String nome;
    private String codigo;
    private Long populacao;
    private boolean isCapital;
    private Long estadoId;
}