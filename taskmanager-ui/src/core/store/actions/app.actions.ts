import { Action } from '@ngrx/store';
import { Credentials, UserModel } from 'src/core/models/user';

export enum AppActionTypes {
  LoginUser = '[App] Login User',
  LoginUserSuccess = '[App] Login User Success',
  RegisterUser = '[App] Register User',
  LogoutUser = '[App] Logout User',
  SetLanguage = '[App] Set Language',
}

// export const LoginUser 
//   = createAction(
//     AppActionTypes.LoginUser,
//     props<{ credentials: Credentials }>()
//   );

export class LoginUser implements Action {
  public readonly type = AppActionTypes.LoginUser;
  public constructor(public payload: { credentials: Credentials }) { }
}

export class LoginUserSuccess implements Action {
  public readonly type = AppActionTypes.LoginUserSuccess;
  public constructor(public payload: { user: UserModel }) { }
}

export class RegisterUser implements Action {
  public readonly type = AppActionTypes.RegisterUser;
}

export class LogoutUser implements Action {
  public readonly type = AppActionTypes.LogoutUser;
}

export class SetLanguage implements Action{
  public readonly type =AppActionTypes.SetLanguage
  public constructor(public payload:{language:string}) {}
}

export type AppActions
= 
| LoginUser
| LoginUserSuccess
| LogoutUser
| RegisterUser
| SetLanguage;
function createAction(LoginUser: AppActionTypes, arg1: any) {
  throw new Error('Function not implemented.');
}

function props<T>(): any {
  throw new Error('Function not implemented.');
}

