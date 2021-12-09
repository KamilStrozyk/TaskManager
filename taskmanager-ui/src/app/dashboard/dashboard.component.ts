import { Component } from '@angular/core';
import  *  as  translations from "../../assets/translations.json"
@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  constructor() {
  }
translations: any = translations;
chosenTranslation : string = "en";
}