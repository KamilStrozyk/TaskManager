import { Action, createAction, props } from '@ngrx/store';
import { TaskList } from 'src/core/models/task-list';
import { TaskSpace } from 'src/core/models/task-space';
import { Task } from 'src/core/models/task';
import { Credentials, RegisterUserModel, UserModel } from 'src/core/models/user';

export enum AppActionTypes {
  LoginUser = '[App] Login User',
  LoginUserSuccess = '[App] Login User Success',
  GetUserTaskSpaces = '[App] Get User Task Spaces',
  GetUserTaskSpacesSuccess = '[App] Get User Task Spaces Success',
  GetUserTaskLists = '[App] Get User Task Lists',
  GetUserTaskListsSuccess = '[App] Get User Task Lists Success',
  GetUserTasks = '[App] Get User Tasks ',
  GetUserTasksSuccess = '[App] Get User Tasks  Success',
  RegisterUser = '[App] Register User',
  RegisterUserSuccess = '[App] Register User Success',
  LogoutUser = '[App] Logout User',
  SetLanguage = '[App] Set Language',
  AddTaskSpace = '[App] Add Task Space',
  UpdateTaskSpace = '[App] Update Task Space',
  RemoveTaskSpace = '[App] Remove Task Space',
  AddTaskList = '[App] Add Task List',
  UpdateTaskList = '[App] Update Task List',
  RemoveTaskList = '[App] Remove Task List',
  AddTask = '[App] Add Task ',
  UpdateTask = '[App] Update Task ',
  RemoveTask = '[App] Remove Task ',
}

export const LoginUser = createAction(
  AppActionTypes.LoginUser,
  props<{ credentials: Credentials }>()
);

export const LoginUserSuccess = createAction(
  AppActionTypes.LoginUserSuccess,
  props<{ user: UserModel }>()
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

export const GetUserTasks = createAction(
  AppActionTypes.GetUserTasks,
  props<{ id: number }>()
);

export const GetUserTaskLists = createAction(
  AppActionTypes.GetUserTaskLists,
  props<{ id: number }>()
);

export const AddTaskSpace = createAction(
  AppActionTypes.AddTaskSpace,
  props<{ taskSpace: TaskSpace }>()
);

export const AddTaskList = createAction(
  AppActionTypes.AddTaskList,
  props<{ taskList: TaskList }>()
);

export const AddTask = createAction(
  AppActionTypes.AddTask,
  props<{ task: Task }>()
);

export const UpdateTaskSpace = createAction(
  AppActionTypes.UpdateTaskSpace,
  props<{ taskSpace: TaskSpace }>()
);

export const RemoveTaskSpace = createAction(
  AppActionTypes.RemoveTaskSpace,
  props<{ id: number }>()
);

export const UpdateTask = createAction(
  AppActionTypes.UpdateTask,
  props<{ task: Task }>()
);

export const RemoveTask = createAction(
  AppActionTypes.RemoveTask,
  props<{ id: number }>()
);

export const UpdateTaskList = createAction(
  AppActionTypes.UpdateTaskList,
  props<{ taskList: TaskList }>()
);

export const RemoveTaskList = createAction(
  AppActionTypes.RemoveTaskList,
  props<{ id: number }>()
);

export const GetUserTaskSpacesSuccess = createAction(
  AppActionTypes.GetUserTaskSpacesSuccess,
  props<{ taskSpaces: Array<TaskSpace> }>()
);

export const GetUserTaskListsSuccess = createAction(
  AppActionTypes.GetUserTaskListsSuccess,
  props<{ taskLists: Array<TaskList> }>()
);

export const GetUserTasksSuccess = createAction(
  AppActionTypes.GetUserTasksSuccess,
  props<{ tasks: Array<Task> }>()
);

export const LogoutUser = createAction(
  AppActionTypes.LogoutUser
);

