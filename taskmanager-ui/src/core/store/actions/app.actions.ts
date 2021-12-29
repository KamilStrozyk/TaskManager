import { Action, createAction, props } from '@ngrx/store';
import { Credentials, RegisterUserModel, UserModel } from 'src/core/models/user';

export enum AppActionTypes {
  LoginUser = '[App] Login User',
  LoginUserSuccess = '[App] Login User Success',
  RegisterUser = '[App] Register User',
  RegisterUserSuccess = '[App] Register User Success',
  LogoutUser = '[App] Logout User',
  SetLanguage = '[App] Set Language',
}

export const LoginUser = createAction(
  AppActionTypes.LoginUser,
  props<{ credentials: Credentials }>()
);

export const LoginUserSuccess = createAction(
  AppActionTypes.LoginUserSuccess,
  props<{ user : UserModel }>()
);

export const RegisterUser = createAction(
  AppActionTypes.RegisterUser,
  props<{ user: RegisterUserModel }>()
);

export const SetLanguage = createAction(
  AppActionTypes.SetLanguage,
  props<{ language: string }>()
);

export const RegisterUserSuccess = createAction(
  AppActionTypes.RegisterUserSuccess
);

export const LogoutUser = createAction(
  AppActionTypes.LogoutUser
);

