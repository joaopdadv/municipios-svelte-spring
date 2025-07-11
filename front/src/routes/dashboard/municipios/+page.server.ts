import { getEstados } from '$lib/api/estados/estadosApi';
import { fail, superValidate } from 'sveltekit-superforms';
import type { PageServerLoad } from './$types';
import { zod } from 'sveltekit-superforms/adapters';
import type { Actions } from '@sveltejs/kit';
import { searchMunicipiosSchema } from './(schema)/searchMunicipiosSchema';
import { getMunicipios } from '$lib/api/municipios/municipiosApi';

export const load: PageServerLoad = async ({ request, url }) => {
    const page = parseInt(url.searchParams.get('page') ?? '0');
    const size = parseInt(url.searchParams.get('size') ?? '10');
    const sort = url.searchParams.get('sort');
    const nome = url.searchParams.get('nome') ?? undefined;
    const capitalStr = url.searchParams.get('capital');
    const populacaoIniStr = url.searchParams.get('populacaoIni');
    const populacaoFimStr = url.searchParams.get('populacaoFim');

    const capital = capitalStr ? (capitalStr === 'true' || capitalStr === '1') : undefined;
    const populacaoIni = populacaoIniStr ? parseInt(populacaoIniStr) : undefined;
    const populacaoFim = populacaoFimStr ? parseInt(populacaoFimStr) : undefined;


    const apiParams: {
        sort: string;
        page: number;
        size: number;
        nome?: string;
        capital?: boolean;
        populacaoIni?: number;
        populacaoFim?: number;
    } = {
        sort: sort || '',
        page,
        size,
        nome,
        capital,
        populacaoIni,
        populacaoFim,
    };

    const municipiosPage = await getMunicipios(apiParams, request.headers.get('cookie'));

    const formData = {
        nome,
        capital: capital ? capital.toString() : undefined,
        populacaoIni,
        populacaoFim,
    };

    const form = await superValidate(formData, zod(searchMunicipiosSchema));

    return {
        municipiosPage,
        form,
    };
};

export const actions: Actions = {
    default: async (event) => {
        const form = await superValidate(event, zod(searchMunicipiosSchema));
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
