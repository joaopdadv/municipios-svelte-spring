import { getEstados } from '$lib/api/estados/estadosApi';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ request }) => {
    const estados = await getEstados(
        {
            sort: 'uf,asc',
            page: 0,
            size: 27,
            // uf: '',
            populacaoIni: 0,
            populacaoFim: 1000000000
        },
        request.headers.get('cookie')
    );

    return {
        estados,
    };
};
