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

@NgModule({
  declarations: [
    HeaderComponent,
    SidenavComponent
  ],
  exports: [
    HeaderComponent,
    SidenavComponent,
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
  ],
  providers: [],
  bootstrap: [HeaderComponent,SidenavComponent]
})
export class SharedModule { }
