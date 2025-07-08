import type { ILoginRequest, ILoginResponse, IRegisterRequest, IUser } from '$lib/types/auth';
import type { AxiosResponse } from 'axios';
import api from '../api';

export async function login(loginData: ILoginRequest, browserCookie: string | null): Promise<AxiosResponse<ILoginResponse>> {
    const headers: Record<string, string> = {};
    if (browserCookie) {
        headers['Cookie'] = browserCookie;
    }

    const response = await api.post<ILoginResponse>('/auth/login', loginData, { headers });
    return response;
}

export async function logout(): Promise<void> {
    await api.post('/auth/logout');
}

export async function register(registerData: IRegisterRequest): Promise<IUser> {
    const response = await api.post<IUser>('/auth/register', registerData);
    return response.data;
}