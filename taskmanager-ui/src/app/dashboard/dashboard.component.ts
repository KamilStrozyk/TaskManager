import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { UserModel } from 'src/core/models/user';
import { getCurrentUser, getLanguage } from 'src/core/store/selectors/app.selectors';
import { IAppState } from 'src/core/store/state/app.state';
import *  as  translations from "../../assets/translations.json"
@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  translations: any = translations;
  chosenTranslation: string = "en";
  currentUser: Observable<UserModel>;
  constructor(
    private store: Store<IAppState>
  ) {
    this.store.select(getLanguage).subscribe(translation => this.chosenTranslation = translation);
    this.currentUser = this.store.select(getCurrentUser);
  }
}