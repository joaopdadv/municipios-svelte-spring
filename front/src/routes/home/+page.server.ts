import type { PageServerLoad } from "./$types.js";
import { setError, superValidate } from "sveltekit-superforms";
import { zod } from "sveltekit-superforms/adapters";
import { loginSchema } from "./(schema)/loginSchema";
import type { Actions } from "../$types.js";
import { fail } from "@sveltejs/kit";
import { login, register } from "$lib/api/auth/authApi.js";
import { registerSchema } from "./(schema)/registerSchema.js";
import { serverCookies } from "$lib/services/cookies.js";

export const load: PageServerLoad = async () => {
    return {
        loginForm: await superValidate(zod(loginSchema)),
        registerForm: await superValidate(zod(registerSchema)), // Assuming you have a register schema similar to login
    };
};

export const actions: Actions = {
    login: async (event) => {
        const form = await superValidate(event, zod(loginSchema));
        if (!form.valid) {
            return fail(400, {
                form,
            });
        }

        try {
            const incomingCookie = event.request.headers.get('cookie');

            const user = await login(form.data, incomingCookie);

            const apiSetCookieHeader = user.headers['set-cookie'];
            if (apiSetCookieHeader) {
                const cookieValue = apiSetCookieHeader[0].split(';')[0].split('=')[1];
                const cookieName = apiSetCookieHeader[0].split(';')[0].split('=')[0];

                event.cookies.set(cookieName.trim(), cookieValue.trim(), {
                    path: '/',
                    httpOnly: true,
                    maxAge: 60 * 60 * 24 * 7,
                    sameSite: 'lax'
                });
            }

            serverCookies.set(event.cookies, 'user', JSON.stringify(user.data), {
                days: 7,
                httpOnly: false
            });

            return {
                form,
            };

        } catch (error) {
            console.error("Erro na API de login:", error);
            const message =
                error instanceof Error
                    ? error.message
                    : "Não foi possível fazer o login.";

            setError(form, "", message);
            return fail(500, { form, message });
        }
    },
    register: async (event) => {
        const form = await superValidate(event, zod(registerSchema));
        if (!form.valid) {
            return fail(400, {
                form,
            });
        }

        try {
            console.log("Dados do formulário de registro:", form.data);
            await register(form.data);

            return {
                form,
            };
        } catch (error) {
            console.error("Erro na API de register:", error);
            const message =
                error instanceof Error
                    ? error.message
                    : "Não foi possível fazer o cadastro. Tente novamente mais tarde.";

            setError(form, "", message);
            return fail(500, { form, message });
        }
    }
};