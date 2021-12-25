import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { TaskSpace } from 'src/core/models/task-space';
import { UserModel } from 'src/core/models/user';
import { getCurrentUser, getLanguage, getUserTaskSpaces } from 'src/core/store/selectors/app.selectors';
import { AddTaskSpace, GetUserTaskSpaces} from 'src/core/store/actions/app.actions';
import { IAppState } from 'src/core/store/state/app.state';
import *  as  translations from "../../assets/translations.json"
import { PopupComponent } from '../shared/popup/popup.component';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  translations: any = translations;
  chosenTranslation: string = "en";
  currentUser: Observable<UserModel>;
  spaces: Observable<TaskSpace[]>;

  constructor(
    private store: Store<IAppState>, public dialog: MatDialog) {
    
    this.store.select(getLanguage).subscribe(translation => this.chosenTranslation = translation);
    this.currentUser = this.store.select(getCurrentUser)
    this.currentUser.subscribe(x =>{ if(x) this.store.dispatch(GetUserTaskSpaces())});
    this.spaces = this.store.select(getUserTaskSpaces);
  }

  showAddDialog(): void {
    const dialogRef = this.dialog.open(PopupComponent, {
      width: '250px',
      data: {
        chosenLanguage: this.chosenTranslation,
        confirm: "add",
        fields: [
            { name: "title", data: "" }
        ]
    }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
      this.store.dispatch(AddTaskSpace({taskSpace:new TaskSpace({title: result[0].data, createdAt: new Date})}));
      }
    });
  }

}