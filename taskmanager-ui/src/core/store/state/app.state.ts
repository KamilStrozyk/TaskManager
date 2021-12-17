import { TaskSpace } from "src/core/models/task-space";
import { UserModel } from "src/core/models/user";

export interface IAppState {
    currentUser?: UserModel,
    language: string
    taskSpaces: Array<TaskSpace>
}

export const initialAppState: IAppState = {
    currentUser: undefined,
    language: "en",
    taskSpaces: []
}

