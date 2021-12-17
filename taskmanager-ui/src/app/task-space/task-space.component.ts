import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { RegisterUserModel } from 'src/core/models/user';
import { RegisterUser } from 'src/core/store/actions/app.actions';
import { getLanguage } from 'src/core/store/selectors/app.selectors';
import { IAppState } from 'src/core/store/state/app.state';
import *  as  translations from "../../assets/translations.json"

@Component({
    selector: 'task-space',
    templateUrl: 'task-space.component.html',
    styleUrls: ['task-space.component.scss'],
})
export class TaskSpaceComponent {
    translations: any = translations;
    chosenTranslation: string = "en";
    hide: boolean = true;
    username: string = "";
    password: string = "";
    email: string = "";
    id: number;
    constructor(
        private store: Store<IAppState>, private route: ActivatedRoute,) {
        this.store.select(getLanguage).subscribe(translation => this.chosenTranslation = translation);
    }
    ngOnInit() {
        this.route.queryParams.subscribe(params => {
          this.id = params['id'];
        });
      }
    signUp() {
        this.store.dispatch(RegisterUser({ user: new RegisterUserModel({ email: this.email, password: this.password, username: this.username }) }));
    }
}