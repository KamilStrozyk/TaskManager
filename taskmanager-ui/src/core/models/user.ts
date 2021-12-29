export interface IRegisterUserModel {
    username?: string;
    email?: string;
    password?: string;
}

export class RegisterUserModel implements IRegisterUserModel {
    username?: string;
    email?: string;
    password?: string;

    constructor(data?: IRegisterUserModel) {
        if (data) {
            for (let property in data) {
                if (data.hasOwnProperty(property)) {
                    (this as any)[property] = (data as any)[property];
                }
            }
        }
    }
}

export interface ICredentials {
    username?: string;
    password?: string;
}

export class Credentials implements ICredentials {
    username?: string;
    password?: string;

    constructor(data?: ICredentials) {
        if (data) {
            for (let property in data) {
                if (data.hasOwnProperty(property)) {
                    (this as any)[property] = (data as any)[property];
                }
            }
        }
    }
}


export interface IUserModel {
    token?: string;
    id?: number;
    type?: string;
    username?: string;
    email?: string;
    roles?: string[];
}

export class UserModel implements IUserModel {

    token?: string;
    id?: number;
    type?: string;
    username?: string;
    email?: string;
    roles?: string[];
    constructor(data?: IUserModel) {
        if (data) {
            for (let property in data) {
                if (data.hasOwnProperty(property)) {
                    (this as any)[property] = (data as any)[property];
                }
            }
        }
    }
}
