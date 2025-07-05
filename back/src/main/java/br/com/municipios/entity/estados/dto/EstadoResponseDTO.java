package br.com.municipios.entity.estados.dto;

import br.com.municipios.entity.municipios.dto.MunicipioResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EstadoResponseDTO {
    private Long id;
    private String uf;
    private Integer codigoUf;
    private List<MunicipioResponseDTO> municipios;
}