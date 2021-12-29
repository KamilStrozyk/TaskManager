import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { UserModel } from 'src/core/models/user';
import { LoginUserSuccess, LogoutUser, SetLanguage } from 'src/core/store/actions/app.actions';
import { getCurrentUser, getLanguage } from 'src/core/store/selectors/app.selectors';
import { IAppState } from 'src/core/store/state/app.state';
import *  as  translations from "../../../assets/translations.json"

@Component({
  selector: 'layout-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./layout.scss']
})
export class SidenavComponent {
  translations: any = translations;
  isDarken: boolean = false;
  chosenTranslation: string = "en";
  currentUser: Observable<UserModel>;

  constructor(
    private store: Store<IAppState>) {
    store.select(getLanguage).subscribe(translation => this.chosenTranslation = translation);
    this.currentUser = this.store.select(getCurrentUser);
    this.currentUser.subscribe(x => {
      if (!x) {
        const user: UserModel = JSON.parse(localStorage.getItem('user'));
        this.store.dispatch(LoginUserSuccess({ user: user }))
      }
    })
  }

  logout() {
    this.store.dispatch(LogoutUser());
  }

  changeLanguage() {
    this.store.dispatch(SetLanguage({ language : this.chosenTranslation === "en" ? "pl" : "en" }));
  }

}