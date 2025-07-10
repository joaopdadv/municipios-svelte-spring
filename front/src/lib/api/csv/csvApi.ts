import api from "../api";

export async function uploadCsv(file: File, cookie: string | null): Promise<string> {
    const formData = new FormData();
    formData.append('file', file);

    const headers: Record<string, string> = {};
    if (cookie) {
        headers['Cookie'] = cookie;
    }

    const response = await api.post<string>('/csv/upload', formData, { headers });

    return response.data;
}