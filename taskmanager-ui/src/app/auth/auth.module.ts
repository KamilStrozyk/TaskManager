import { NgModule } from "@angular/core";
import { SharedModule } from "../shared/shared.module";
import { RegisterComponent } from "./register/register.component";
import {MatFormFieldModule} from '@angular/material/form-field'; 
import {MatDividerModule} from '@angular/material/divider'; 
@NgModule({
    declarations: [
        RegisterComponent
    ],
    exports: [
        MatFormFieldModule
    ],
    imports: [
        SharedModule,
        MatFormFieldModule,
        MatDividerModule
    ],
    providers: [],
    bootstrap: [RegisterComponent]
})
export class AuthModule { }
