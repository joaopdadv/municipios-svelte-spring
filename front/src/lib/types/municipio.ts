export interface IMunicipio {
    id: number;
    nome: string;
    codigo: string;
    populacao: number;
    capital: boolean;
    estado: {
        id: number;
        uf: string;
        codigoUf: number;
    };
}

export interface IMunicipioRequest {
    sort: string;
    page: number;
    size: number;
    nome?: string;
    capital?: boolean;
    populacaoIni?: number;
    populacaoFim?: number;
}