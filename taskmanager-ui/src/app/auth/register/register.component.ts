import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { RegisterUserModel } from 'src/core/models/user';
import { RegisterUser } from 'src/core/store/actions/app.actions';
import { getLanguage } from 'src/core/store/selectors/app.selectors';
import { IAppState } from 'src/core/store/state/app.state';
import *  as  translations from "../../../assets/translations.json"

@Component({
  selector: 'register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.scss'],
})
export class RegisterComponent {
  translations: any = translations;
  chosenTranslation: string = "en";
  hide: boolean = true;
  username: string = "";
  password: string = "";
  email: string = "";

  constructor(
    private store: Store<IAppState>) {
    this.store.select(getLanguage).subscribe(translation => this.chosenTranslation = translation);
  }

  signUp() {
    this.store.dispatch(RegisterUser({ user: new RegisterUserModel({ email: this.email, password: this.password, username: this.username }) }));
  }
}