import { getEstados } from '$lib/api/estados/estadosApi';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ request, url }) => {
    const page = parseInt(url.searchParams.get('page') ?? '0');
    const size = parseInt(url.searchParams.get('size') ?? '10');
    // 1. Leia o parâmetro 'sort' da URL
    const sort = url.searchParams.get('sort');

    // 2. Construa o objeto de parâmetros para a API
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
        estadosPage
    };
};
