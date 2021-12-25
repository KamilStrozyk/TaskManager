import { Action, createAction, props } from '@ngrx/store';
import { TaskSpace } from 'src/core/models/task-space';
import { Credentials, RegisterUserModel, UserModel } from 'src/core/models/user';

export enum AppActionTypes {
  LoginUser = '[App] Login User',
  LoginUserSuccess = '[App] Login User Success',
  GetUserTaskSpaces = '[App] Get User Task Spaces',
  GetUserTaskSpacesSuccess = '[App] Get User Task Spaces Success',
  RegisterUser = '[App] Register User',
  RegisterUserSuccess = '[App] Register User Success',
  LogoutUser = '[App] Logout User',
  SetLanguage = '[App] Set Language',
  AddTaskSpace = '[App] Add Task Space',
  UpdateTaskSpace = '[App] Update Task Space',
  RemoveTaskSpace = '[App] Remove Task Space',
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

export const GetUserTaskSpaces = createAction(
  AppActionTypes.GetUserTaskSpaces
);

export const AddTaskSpace = createAction(
  AppActionTypes.AddTaskSpace,
  props<{ taskSpace: TaskSpace}>()
);

export const UpdateTaskSpace = createAction(
  AppActionTypes.UpdateTaskSpace,
  props<{ taskSpace: TaskSpace}>()
);

export const RemoveTaskSpace = createAction(
  AppActionTypes.RemoveTaskSpace,
  props<{ id: number}>()
);

export const GetUserTaskSpacesSuccess = createAction(
  AppActionTypes.GetUserTaskSpacesSuccess,
  props<{ taskSpaces: Array<TaskSpace> }>()
);

export const LogoutUser = createAction(
  AppActionTypes.LogoutUser
);

