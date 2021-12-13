import { UserModel } from "src/core/models/user";

export interface IAppState {
    currentUser?: UserModel,
    language: string
}

export const initialAppState: IAppState = {
    currentUser: undefined,
    language: "en"
}

