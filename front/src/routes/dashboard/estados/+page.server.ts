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
    const uf = url.searchParams.get('uf') ?? undefined;
    const populacaoIniStr = url.searchParams.get('populacaoIni');
    const populacaoFimStr = url.searchParams.get('populacaoFim');

    const populacaoIni = populacaoIniStr ? parseInt(populacaoIniStr) : undefined;
    const populacaoFim = populacaoFimStr ? parseInt(populacaoFimStr) : undefined;

    const apiParams: {
        sort: string;
        page: number;
        size: number;
        uf?: string;
        populacaoIni?: number;
        populacaoFim?: number;
    } = {
        sort: sort || '',
        page,
        size,
        uf,
        populacaoIni,
        populacaoFim,
    };

    const estadosPage = await getEstados(apiParams, request.headers.get('cookie'));

    const formData = {
        uf,
        populacaoIni,
        populacaoFim,
    };

    const form = await superValidate(formData, zod(searchEstadosSchema));

    return {
        estadosPage,
        form,
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
