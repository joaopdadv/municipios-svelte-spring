import type { IMunicipio } from "./municipio";

export interface IEstado {
    id: number;
    uf: string;
    codigoUf: number;
    populacaoTotal: number;
    municipioMaisPopuloso: IMunicipio;
}

export interface IEstadoRequest {
    sort: string;
    page: number;
    size: number;
    uf?: string;
    populacaoIni?: number;
    populacaoFim?: number;
}