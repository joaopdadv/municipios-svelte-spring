package br.com.municipios.entity.csv;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CSVEntity {
    private String uf;

    private Integer codigoUf;

    private String codigoMunicipio;

    private String nomeMunicipio;

    private String capitalDeEstado;

    private Integer populacao;
}