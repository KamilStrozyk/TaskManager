export interface ITaskList {
    id?: number;
    spaceId?: number;
    title?: string;
    createdAt?: Date;
}

export class TaskList implements ITaskList {
    id?: number;
    spaceId?: number;
    title?: string;
    createdAt?: Date;

    constructor(data?: ITaskList) {
        if (data) {
            for (let property in data) {
                if (data.hasOwnProperty(property)) {
                    (this as any)[property] = (data as any)[property];
                }
            }
        }
    }
}
