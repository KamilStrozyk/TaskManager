import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TaskSpaceComponent } from './task-space/task-space.component';

const routes: Routes = [
  {path: '',component: DashboardComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'space/:id', canActivate: [AuthGuard], component: TaskSpaceComponent},
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
