import { z } from "zod";

export const searchEstadosSchema = z.object({
    uf: z.string().optional(),
    populacaoIni: z.number().optional(),
    populacaoFim: z.number().optional(),
});

export type SearchEstadosSchema = typeof searchEstadosSchema;