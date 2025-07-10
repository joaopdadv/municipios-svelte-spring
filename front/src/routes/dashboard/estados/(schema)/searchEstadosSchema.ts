import { z } from "zod";

export const searchEstadosSchema = z.object({
    uf: z.string().optional(),
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

export type SearchEstadosSchema = typeof searchEstadosSchema;