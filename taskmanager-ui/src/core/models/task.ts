export interface ITask {
    id?: number;
    taskListId?: number;
    title?: string;
    description?: string;
    createdAt?: Date;
    finished?: boolean;
}

export class Task implements ITask {
    id?: number;
    taskListId?: number;
    title?: string;
    description?: string;
    createdAt?: Date;
    finished?: boolean;

    constructor(data?: ITask) {
        if (data) {
            for (let property in data) {
                if (data.hasOwnProperty(property)) {
                    (this as any)[property] = (data as any)[property];
                }
            }
        }
    }
}
