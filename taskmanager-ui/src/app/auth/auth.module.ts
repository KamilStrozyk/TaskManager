import { NgModule } from "@angular/core";
import { SharedModule } from "../shared/shared.module";
import { RegisterComponent } from "./register/register.component";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDividerModule } from '@angular/material/divider';
import { FormsModule } from "@angular/forms";
import { LoginComponent } from "./login/login.component";
@NgModule({
    declarations: [
        RegisterComponent,
        LoginComponent
    ],
    exports: [
        MatFormFieldModule
    ],
    imports: [
        SharedModule,
        MatFormFieldModule,
        MatDividerModule,
        FormsModule
    ],
    providers: [],
    bootstrap: [RegisterComponent, LoginComponent]
})
export class AuthModule { }
