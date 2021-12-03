import { NgModule } from "@angular/core";
import { HeaderComponent } from "./layout/header.component";
import {MatToolbarModule} from '@angular/material/toolbar'; 
import { SidenavComponent } from "./layout/sidenav.component";
import {MatSidenavModule} from '@angular/material/sidenav'; 
import {MatIconModule} from '@angular/material/icon'; 
import {MatButtonModule} from '@angular/material/button'; 
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
    MatButtonModule
  ],
  imports: [
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [HeaderComponent,SidenavComponent]
})
export class SharedModule { }
