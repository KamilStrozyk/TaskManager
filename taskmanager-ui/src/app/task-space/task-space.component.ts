import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { TaskList } from 'src/core/models/task-list';
import { TaskSpace } from 'src/core/models/task-space';
import { AddTaskList, GetUserTaskLists, GetUserTaskSpaces, RemoveTaskSpace, UpdateTaskSpace } from 'src/core/store/actions/app.actions';
import { getLanguage, getUserTaskLists, getUserTaskSpaces } from 'src/core/store/selectors/app.selectors';
import { IAppState } from 'src/core/store/state/app.state';
import *  as  translations from "../../assets/translations.json"
import { PopupComponent, PopupData } from '../shared/popup/popup.component';

@Component({
    selector: 'task-space',
    templateUrl: 'task-space.component.html',
    styleUrls: ['task-space.component.scss'],
})
export class TaskSpaceComponent {
    translations: any = translations;
    chosenTranslation: string = "en";
    taskSpace: TaskSpace = new TaskSpace;
    taskLists: Array<TaskList> = [];
    id: number;

    constructor(
        private store: Store<IAppState>, private route: ActivatedRoute, public dialog: MatDialog, private router: Router) {
        this.store.select(getLanguage).subscribe(translation => this.chosenTranslation = translation);
        this.store.dispatch(GetUserTaskSpaces())
    }

    ngOnInit() {
        this.route.paramMap.subscribe(params => {
            this.id = parseInt(params.get('id'));
            this.store.dispatch(GetUserTaskLists({ id: this.id }))
            this.store.select(getUserTaskSpaces)
                .subscribe(taskSpaces => {
                    if (taskSpaces && this.id)
                        this.taskSpace = taskSpaces.find(x => x.id == this.id);
                });

            this.store.select(getUserTaskLists)
                .subscribe(taskLists => {
                    if (taskLists && this.id)
                        this.taskLists = taskLists;
                });

        });
    }

    edit() {
        const dialogRef = this.dialog.open(PopupComponent, {
            width: '250px',
            data: {
                chosenLanguage: this.chosenTranslation,
                confirm: "edit",
                fields: [
                    new PopupData({ name: "title", data: this.taskSpace.title })
                ]
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.store.dispatch(UpdateTaskSpace({ taskSpace: new TaskSpace({ id: this.id, title: result[0].data, createdAt: this.taskSpace.createdAt }) }));
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
                this.store.dispatch(RemoveTaskSpace({ id: this.id }));
                this.router.navigate(['/']);
            }
        });
    }

    addList() {
        const dialogRef = this.dialog.open(PopupComponent, {
            width: '250px',
            data: {
                chosenLanguage: this.chosenTranslation,
                confirm: "add",
                fields: [
                    new PopupData({ name: "title", data: "" })
                ]
            }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.store.dispatch(AddTaskList({ taskList: new TaskList({ id: this.id, spaceId: this.id, title: result[0].data, createdAt: this.taskSpace.createdAt }) }));
            }
        });
    }
}