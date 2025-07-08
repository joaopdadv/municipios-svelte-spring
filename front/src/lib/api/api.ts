import axios, { type AxiosRequestConfig } from 'axios';
import { PUBLIC_API_BASE_URL } from '$env/static/public';
import { goto } from '$app/navigation';
import { redirect } from '@sveltejs/kit';

const apiConfig: AxiosRequestConfig = {
    baseURL: PUBLIC_API_BASE_URL,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json'
    }
};

const api = axios.create(apiConfig);

api.interceptors.request.use(
    config => {
        const timestamp = new Date().toLocaleTimeString();
        // console.groupCollapsed(`[API Request] ${config.method?.toUpperCase()} ${config.url} @ ${timestamp}`);
        // console.log('URL Completa:', `${config.baseURL}${config.url}`);
        // console.log('Cabeçalhos:', config.headers);
        // if (config.data) {
        //     console.log('Corpo (Payload):', config.data);
        // }
        // console.groupEnd();

        return config;
    },
    error => {
        console.error('Erro no interceptor de requisição:', error);
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    response => {
        // console.log(response.headers);
        return response;
    },
    error => {
        if (error.status === 401) {
            redirect(302, '/home');
        }
        console.log(error.status)
        console.error('Erro na chamada da API:', error.response?.data || error.message);
        return Promise.reject(new Error(error.response?.data?.message || error.message));
    }
);

export default api;