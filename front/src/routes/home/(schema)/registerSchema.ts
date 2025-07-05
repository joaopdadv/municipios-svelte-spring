import { USER_ROLE } from "$lib/enums/auth";
import { z } from "zod";

export const registerSchema = z
    .object({
        email: z.string().email("Por favor, insira um e-mail válido."),
        password: z
            .string()
            .min(6, "A senha deve ter no mínimo 6 caracteres."),
        confirmPassword: z
            .string()
            .min(6, "A confirmação de senha é obrigatória."),
        role: z.nativeEnum(USER_ROLE).default(USER_ROLE.ALUNO),
    })
    .superRefine(({ password, confirmPassword }, ctx) => {
        if (password !== confirmPassword) {
            ctx.addIssue({
                code: "custom",
                message: "As senhas não coincidem.",
                path: ["confirmPassword"],
            });
        }
    });

export type RegisterSchema = typeof registerSchema;