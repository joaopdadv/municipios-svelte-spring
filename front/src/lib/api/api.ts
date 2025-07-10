import axios, { type AxiosRequestConfig } from 'axios';
import { PUBLIC_API_BASE_URL } from '$env/static/public';
import { redirect } from '@sveltejs/kit';
import { browser } from '$app/environment';
import { goto } from '$app/navigation';

const apiConfig: AxiosRequestConfig = {
    baseURL: PUBLIC_API_BASE_URL,
    withCredentials: true,
};

const api = axios.create(apiConfig);

api.interceptors.request.use(
    config => {
        const isFormData = config.data instanceof FormData;

        if (!isFormData) {
            config.headers['Content-Type'] = 'application/json';
        }

        return config;
    },
    error => {
        console.error('Erro no interceptor de requisição:', error);
        return Promise.reject(error);
    }
);


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
        const status = error.response?.status;
        if (status === 401) {
            if (browser) {
                goto('/home');
            } else {
                throw redirect(302, '/home');
            }
        }

        console.log(error.status)
        console.error('Erro na chamada da API:', error.response?.data || error.message);
        return Promise.reject(new Error(error.response?.data?.message || error.message));
    }
);

export default api;