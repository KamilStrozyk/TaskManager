import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { TaskList } from 'src/core/models/task-list';
import { GetUserTaskLists, RemoveTaskList, UpdateTaskList } from 'src/core/store/actions/app.actions';
// import { GetUserTaskLists, RemoveTaskList, UpdateTaskList } from 'src/core/store/actions/app.actions';
import { getLanguage, getUserTaskLists } from 'src/core/store/selectors/app.selectors';
import { IAppState } from 'src/core/store/state/app.state';
import *  as  translations from "../../assets/translations.json"
import { PopupComponent, PopupData } from '../shared/popup/popup.component';

@Component({
    selector: 'task-list',
    templateUrl: 'task-list.component.html',
    styleUrls: ['task-list.component.scss'],
})
export class TaskListComponent {
    translations: any = translations;
    chosenTranslation: string = "en";
    @Input() taskList: TaskList;

    constructor(
        private store: Store<IAppState>, private route: ActivatedRoute, public dialog: MatDialog, private router: Router) {
        this.store.select(getLanguage).subscribe(translation => this.chosenTranslation = translation);
    }

    ngOnInit() {
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
            if(result){
            this.store.dispatch(UpdateTaskList({ taskList: new TaskList({ id: this.taskList.id, title: result[0].data, spaceId: this.taskList.spaceId,  createdAt: this.taskList.createdAt }) }));
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
            if(result){
                this.store.dispatch(RemoveTaskList({ id: this.taskList.id }));
            }
        });
    }
}