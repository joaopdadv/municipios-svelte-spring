import type { IEstado, IEstadoRequest } from "$lib/types/estados";
import api from "../api";
import type { IPage } from "$lib/types/auth";

export async function getEstados(request: IEstadoRequest, cookie: string | null): Promise<IPage<IEstado>> {

    const headers: Record<string, string> = {};
    if (cookie) {
        headers['Cookie'] = cookie;
    }

    const response = await api.get<IPage<IEstado>>('/estados', {
        params: request,
        headers: headers
    });

    return response.data;
}