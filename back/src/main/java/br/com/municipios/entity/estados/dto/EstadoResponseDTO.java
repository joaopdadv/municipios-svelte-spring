package br.com.municipios.entity.estados.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class EstadoResponseDTO {
    private Long id;
    private String uf;
    private Integer codigoUf;
//    private List<MunicipioResponseDTO> municipios;
}