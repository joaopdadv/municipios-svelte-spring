import type { IEstado, IEstadoRequest } from "$lib/types/estados";
import api from "../api";

export async function getEstados(request: IEstadoRequest, cookie: string | null): Promise<IEstado[]> {

    const headers: Record<string, string> = {};
    if (cookie) {
        headers['Cookie'] = cookie;
    }

    const response = await api.get<IEstado[]>('/estados', {
        params: request,
        headers: headers
    });

    return response.data;
}