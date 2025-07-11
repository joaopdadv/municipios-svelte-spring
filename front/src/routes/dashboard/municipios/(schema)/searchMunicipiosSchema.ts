import { z } from "zod";

export const searchMunicipiosSchema = z.object({
    nome: z.string().optional(),
    capital: z.string().optional(),
    populacaoIni: z
        .preprocess((val) => {
            if (val === '' || val === null || val === undefined) return undefined;
            return Number(val);
        }, z.number().int().nonnegative().optional()),

    populacaoFim: z
        .preprocess((val) => {
            if (val === '' || val === null || val === undefined) return undefined;
            return Number(val);
        }, z.number().int().nonnegative().optional()),

});

export type SearchMunicipiosSchema = typeof searchMunicipiosSchema;