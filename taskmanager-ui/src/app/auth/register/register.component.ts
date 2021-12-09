import {Component} from '@angular/core';
import  *  as  translations from "../../../assets/translations.json"

@Component({
  selector: 'register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.scss'],
})
export class RegisterComponent {
  translations: any = translations;
  chosenTranslation : string = "en";
  hide : boolean = true;
  }