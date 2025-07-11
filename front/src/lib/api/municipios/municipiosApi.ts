import api from "../api";
import type { IPage } from "$lib/types/auth";
import type { IMunicipio, IMunicipioRequest } from "$lib/types/municipio";

export async function getMunicipios(request: IMunicipioRequest, cookie: string | null): Promise<IPage<IMunicipio>> {

    const headers: Record<string, string> = {};
    if (cookie) {
        headers['Cookie'] = cookie;
    }

    const response = await api.get<IPage<IMunicipio>>('/municipios', {
        params: request,
        headers: headers
    });

    return response.data;
}