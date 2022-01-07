import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Task } from 'src/core/models/task';
import { TaskList } from 'src/core/models/task-list';
import { TaskService } from 'src/core/services/task.service';
import { RemoveTask, UpdateTask } from 'src/core/store/actions/app.actions';
import { getLanguage } from 'src/core/store/selectors/app.selectors';
import { IAppState } from 'src/core/store/state/app.state';
import *  as  translations from "../../assets/translations.json"
import { OptionData, PopupComponent, PopupData } from '../shared/popup/popup.component';

@Component({
    selector: 'task',
    templateUrl: 'task.component.html',
    styleUrls: ['task.component.scss'],
})
export class TaskComponent {
    translations: any = translations;
    chosenTranslation: string = "en";
    @Input() task: Task;
    @Input() availableTaskLists: Array<TaskList>;

    constructor(
        private store: Store<IAppState>, private route: ActivatedRoute, public dialog: MatDialog, private taskService: TaskService) {
        this.store.select(getLanguage).subscribe(translation => this.chosenTranslation = translation);
    }

    check() {
        this.taskService.updateTask(this.task).subscribe();
    }

    edit() {
        const dialogRef = this.dialog.open(PopupComponent, {
            width: '250px',
            data: {
                chosenLanguage: this.chosenTranslation,
                confirm: "edit",
                fields: [
                    new PopupData({ name: "title", data: this.task.title }),
                    new PopupData({ name: "description", data: this.task.description }),
                    new PopupData({
                        name: "task_list", data: this.task.taskListId, choice: true,
                        options: this.availableTaskLists.map(taskList => new OptionData({ value: taskList.id, name: taskList.title }))
                    })
                ]
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.store.dispatch(UpdateTask({ task: new Task({ id: this.task.id, title: result[0].data, taskListId: parseInt(result[2].data), description: result[1].data, createdAt: this.task.createdAt }) }));
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
                this.store.dispatch(RemoveTask({ id: this.task.id }));
            }
        });
    }
}