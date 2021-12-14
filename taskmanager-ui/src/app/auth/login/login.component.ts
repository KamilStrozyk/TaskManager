import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Credentials } from 'src/core/models/user';
import { LoginUser } from 'src/core/store/actions/app.actions';
import { getLanguage } from 'src/core/store/selectors/app.selectors';
import { IAppState } from 'src/core/store/state/app.state';
import *  as  translations from "../../../assets/translations.json"

@Component({
  selector: 'login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.scss'],
})
export class LoginComponent {
  translations: any = translations;
  chosenTranslation: string = "en";
  hide: boolean = true;
  username: string = "";
  password: string = "";

  constructor(
    private store: Store<IAppState>) {
    this.store.select(getLanguage).subscribe(translation => this.chosenTranslation = translation);
  }

  signIn() {
    this.store.dispatch(LoginUser({ credentials: new Credentials({ username: this.username, password: this.password }) }));
  }

}