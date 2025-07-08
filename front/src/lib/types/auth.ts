import type { USER_ROLE } from "$lib/enums/auth";

export interface IUser {
    id: string;
    email: string;
    role: USER_ROLE;
}


export interface ILoginRequest {
    email: string;
    password: string;
}

export interface ILoginResponse {
    user: IUser;
}

export interface IRegisterRequest {
    email: string;
    password: string;
    role: Omit<USER_ROLE, USER_ROLE.ADMIN>;
}

export interface IPage<T> {
    content: Array<T>;
    page: {
        size: number;
        number: number;
        totalElements: number;
        totalPages: number;
    }
}