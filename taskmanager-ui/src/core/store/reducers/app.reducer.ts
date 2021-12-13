import { AppActionTypes, AppActions } from '../actions/app.actions';
import { IAppState, initialAppState } from '../state/app.state';

export function appReducer(state = initialAppState, action: AppActions): IAppState {
  switch (action.type) {
    case AppActionTypes.LoginUserSuccess:
      return {
        ...state,
        currentUser: action.payload.user
      };
    case AppActionTypes.SetLanguage: 
      return{
        ...state,
        language: action.payload.language
      };
    default:
      return state;
  }
}
