import { Task } from "src/core/models/task";
import { TaskList } from "src/core/models/task-list";
import { TaskSpace } from "src/core/models/task-space";
import { UserModel } from "src/core/models/user";

export interface IAppState {
    currentUser?: UserModel,
    language: string
    taskSpaces: Array<TaskSpace>
    taskLists: Array<TaskList>
    tasks: Array<Task>
}

export const initialAppState: IAppState = {
    currentUser: undefined,
    language: "en",
    taskSpaces: [],
    taskLists: [],
    tasks: []
}

