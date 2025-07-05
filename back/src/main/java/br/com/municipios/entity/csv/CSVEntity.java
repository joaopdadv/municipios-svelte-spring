package br.com.municipios.entity.csv;

import lombok.Data;

@Data
public class CSVEntity {
    private String uf;

    private Integer codigoUf;

    private String codigoMunicipio;

    private String nomeMunicipio;

    private String capitalDeEstado;

    private Integer populacao;
}