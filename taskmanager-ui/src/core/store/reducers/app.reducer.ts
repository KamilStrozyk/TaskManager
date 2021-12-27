import { createReducer, on } from '@ngrx/store';
import { GetUserTaskListsSuccess, GetUserTaskSpacesSuccess, GetUserTasksSuccess, LoginUserSuccess, SetLanguage } from '../actions/app.actions';
import { initialAppState } from '../state/app.state';

export const appReducer = createReducer(
  initialAppState,
  on(LoginUserSuccess, (state, {user}) => ({...state, currentUser: user})),
  on(SetLanguage, (state, {language}) => ({ ...state, language: language })),
  on(GetUserTaskSpacesSuccess, (state, {taskSpaces}) => ({ ...state, taskSpaces: taskSpaces })),
  on(GetUserTasksSuccess, (state, {tasks}) => ({ ...state, tasks: tasks })),
  on(GetUserTaskListsSuccess, (state, {taskLists}) => ({ ...state, taskLists: taskLists }))
);
