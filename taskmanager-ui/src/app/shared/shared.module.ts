import { NgModule } from "@angular/core";
import { HeaderComponent } from "./layout/header.component";
import {MatToolbarModule} from '@angular/material/toolbar'; 
import { SidenavComponent } from "./layout/sidenav.component";
import {MatSidenavModule} from '@angular/material/sidenav'; 
import {MatIconModule} from '@angular/material/icon'; 
import {MatButtonModule} from '@angular/material/button'; 
import { BrowserModule } from '@angular/platform-browser';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list'; 
import {MatDialogModule} from '@angular/material/dialog';
import { PopupComponent } from "./popup/popup.component";
import { FormsModule } from "@angular/forms";

@NgModule({
  declarations: [
    HeaderComponent,
    SidenavComponent,
    PopupComponent
  ],
  exports: [
    HeaderComponent,
    SidenavComponent,
    PopupComponent,
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    BrowserModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    BrowserModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatGridListModule,
    
  ],
  imports: [
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    BrowserModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatGridListModule,
    MatDialogModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [HeaderComponent,SidenavComponent, PopupComponent]
})
export class SharedModule { }
