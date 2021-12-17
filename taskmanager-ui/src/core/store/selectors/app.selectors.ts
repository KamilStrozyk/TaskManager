import { IAppState } from '../state/app.state';
import { createSelector, createFeatureSelector } from '@ngrx/store';
export const appSelector = createFeatureSelector<IAppState>('app');

export const getCurrentUser = createSelector(appSelector, (app: IAppState) => app.currentUser);
export const getUserTaskSpaces = createSelector(appSelector, (app: IAppState) => app.taskSpaces);
export const getLanguage = createSelector(appSelector, (app: IAppState) => app.language);
