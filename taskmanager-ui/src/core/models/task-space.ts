export interface ITaskSpace {
    id?: number;
    title?: string;
    createdAt?: Date;
}

export class TaskSpace implements ITaskSpace {
    id?: number;
    title?: string;
    createdAt?: Date;

    constructor(data?: ITaskSpace) {
        if (data) {
            for (let property in data) {
                if (data.hasOwnProperty(property)) {
                    (this as any)[property] = (data as any)[property];
                }
            }
        }
    }
}
