import { Component } from '@angular/core';

@Component({
  selector: 'layout-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./layout.scss']
})
export class SidenavComponent {
  constructor() { }

  isDarken: boolean = false;
}