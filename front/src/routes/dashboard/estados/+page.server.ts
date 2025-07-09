import { getEstados } from '$lib/api/estados/estadosApi';
import { fail, superValidate } from 'sveltekit-superforms';
import type { PageServerLoad } from './$types';
import { zod } from 'sveltekit-superforms/adapters';
import { searchEstadosSchema } from './(schema)/searchEstadosSchema';
import type { Actions } from '@sveltejs/kit';

export const load: PageServerLoad = async ({ request, url }) => {
    const page = parseInt(url.searchParams.get('page') ?? '0');
    const size = parseInt(url.searchParams.get('size') ?? '10');
    const sort = url.searchParams.get('sort');

    const apiParams: {
        sort: string;
        page: number;
        size: number;
        populacaoIni: number;
        populacaoFim: number;
    } = {
        sort: sort || '',
        page,
        size,
        populacaoIni: 0,
        populacaoFim: 1000000000
    };

    const estadosPage = await getEstados(apiParams, request.headers.get('cookie'));

    return {
        estadosPage,
        form: await superValidate(zod(searchEstadosSchema)),
    };
};

export const actions: Actions = {
    default: async (event) => {
        const form = await superValidate(event, zod(searchEstadosSchema));
        if (!form.valid) {
            return fail(400, {
                form: form,
            });
        }
        return {
            form: form,
        };
    },
};
