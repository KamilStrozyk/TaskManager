import { createReducer, on } from '@ngrx/store';
import { LoginUserSuccess, SetLanguage } from '../actions/app.actions';
import { initialAppState } from '../state/app.state';

export const appReducer = createReducer(
  initialAppState,
  on(LoginUserSuccess, (state, {user}) => ({...state, currentUser: user})),
  on(SetLanguage, (state, {language}) => ({ ...state, language: language }))
  
);
