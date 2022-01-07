import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { TaskList } from 'src/core/models/task-list';
import { Task } from 'src/core/models/task';
import { GetUserTasks, RemoveTaskList, UpdateTaskList, AddTask } from 'src/core/store/actions/app.actions';
import { getLanguage, getUserTasks } from 'src/core/store/selectors/app.selectors';
import { IAppState } from 'src/core/store/state/app.state';
import *  as  translations from "../../assets/translations.json"
import { PopupComponent, PopupData } from '../shared/popup/popup.component';
import { TaskService } from 'src/core/services/task.service';

@Component({
    selector: 'task-list',
    templateUrl: 'task-list.component.html',
    styleUrls: ['task-list.component.scss'],
})
export class TaskListComponent {
    translations: any = translations;
    chosenTranslation: string = "en";
    @Input() taskList: TaskList;
    tasks: Array<Task>;
    @Input() availableTaskLists: Array<TaskList>;
    
    constructor(
        private store: Store<IAppState>, private route: ActivatedRoute, public dialog: MatDialog, private taskService: TaskService) {
        this.store.select(getLanguage).subscribe(translation => this.chosenTranslation = translation);
    }
    
    ngOnInit() {
        this.taskService.getUserTasks(this.taskList.id).subscribe(tasks => this.tasks = tasks);
    }

    edit() {
        const dialogRef = this.dialog.open(PopupComponent, {
            width: '250px',
            data: {
                chosenLanguage: this.chosenTranslation,
                confirm: "edit",
                fields: [
                    new PopupData({ name: "title", data: this.taskList.title })
                ]
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.store.dispatch(UpdateTaskList({ taskList: new TaskList({ id: this.taskList.id, title: result[0].data, spaceId: this.taskList.spaceId, createdAt: this.taskList.createdAt }) }));
            }
        });
    }

    remove() {
        const dialogRef = this.dialog.open(PopupComponent, {
            width: '250px',
            data: {
                chosenLanguage: this.chosenTranslation,
                confirm: "ok",
                message: "confirmation"
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.store.dispatch(RemoveTaskList({ id: this.taskList.id }));
            }
        });
    }

    addTask() {
        const dialogRef = this.dialog.open(PopupComponent, {
            width: '250px',
            data: {
                chosenLanguage: this.chosenTranslation,
                confirm: "add",
                fields: [
                    new PopupData({ name: "title", data: "" }),
                    new PopupData({ name: "description", data: "" })
                ]
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.store.dispatch(AddTask({ task: new Task({ taskListId: this.taskList.id, title: result[0].data, description: result[1].data, createdAt: new Date() }) }));
            }
        });
    }
}