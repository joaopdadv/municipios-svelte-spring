// +page.server.ts
import { getEstados } from '$lib/api/estados/estadosApi';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ request, url }) => {
    const page = parseInt(url.searchParams.get('page') ?? '0');
    const size = parseInt(url.searchParams.get('size') ?? '10');

    const estadosPage = await getEstados(
        {
            sort: 'uf,asc',
            page: page,
            size,
            populacaoIni: 0,
            populacaoFim: 1000000000
        },
        request.headers.get('cookie')
    );

    return {
        estadosPage
    };
};
