import type { ILoginRequest, ILoginResponse, IRegisterRequest, IUser } from '$lib/types/auth';
import api from '../api';

export async function login({ email, password }: ILoginRequest): Promise<IUser> {

    const response = await api.post<IUser>('/auth/login', {
        email,
        password
    });

    return response.data;
}

export async function logout(): Promise<void> {
    await api.post('/auth/logout');
}

export async function register({ email, password, role }: IRegisterRequest): Promise<IUser> {
    const response = await api.post<IUser>('/auth/register', {
        email,
        password,
        role
    });

    return response.data;
}