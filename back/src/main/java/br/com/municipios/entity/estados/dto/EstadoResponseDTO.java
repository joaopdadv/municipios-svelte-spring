package br.com.municipios.entity.estados.dto;

import br.com.municipios.entity.municipios.Municipio;
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
    private Long populacaoTotal;
    private Municipio municipioMaisPopuloso;
}