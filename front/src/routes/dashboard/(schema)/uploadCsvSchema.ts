import { z } from "zod";

export const uploadCsvSchema = z.object({
    file: z
        .instanceof(File, { message: "Você deve selecionar um arquivo." })
        .refine((file) => file.size > 0, "Arquivo não pode estar vazio."),
});

export type UploadCsvSchema = typeof uploadCsvSchema;