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
    token: string;
}

export interface IRegisterRequest {
    email: string;
    password: string;
    role: Omit<USER_ROLE, USER_ROLE.ADMIN>;
}

