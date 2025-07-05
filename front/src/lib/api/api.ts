import axios, { type AxiosRequestConfig } from 'axios';
import { PUBLIC_API_BASE_URL } from '$env/static/public';

const apiConfig: AxiosRequestConfig = {
    baseURL: PUBLIC_API_BASE_URL,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json'
    }
};

const api = axios.create(apiConfig);

api.interceptors.response.use(
    response => response,
    error => {
        console.error('Erro na chamada da API:', error.response?.data || error.message);
        return Promise.reject(new Error(error.response?.data?.message || error.message));
    }
);

export default api;