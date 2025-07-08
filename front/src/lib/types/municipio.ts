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