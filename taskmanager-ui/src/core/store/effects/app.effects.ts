
import { Injectable } from '@angular/core';
import { Actions, createEffect, Effect, ofType } from '@ngrx/effects';
import { AppActionTypes, LoginUserSuccess } from '../actions/app.actions';


import { catchError, map, mergeMap, switchMap } from 'rxjs/operators';
import { EMPTY } from 'rxjs';
import { AuthService } from 'src/core/services/auth.service';
import { UserModel } from 'src/core/models/user';

@Injectable()
export class AppEffects {

    loginUser$ = createEffect(() => this.actions$.pipe(
        ofType(AppActionTypes.LoginUser),
        mergeMap((action) => this.authService.loginUser(action.payload.credentials)
            .pipe(
                map((user: UserModel) => new LoginUserSuccess({ user: user })),
                catchError(() => EMPTY)
            ))));

    registerUser$ = createEffect(() => this.actions$.pipe(
        ofType(AppActionTypes.LoginUser),
        mergeMap((action) => this.authService.registerUser(action.payload.)
            .pipe(
                map((user: UserModel) => new LoginUserSuccess({ user: user })),
                catchError(() => EMPTY)
            ))));

    logoutUser$ = createEffect(() => this.actions$.pipe(
        ofType(AppActionTypes.LoginUser),
        switchMap(async () => new LoginUserSuccess({ user: null })),
        catchError(() => EMPTY)
    ));

    constructor(
        private actions$: Actions,
        private authService: AuthService,
    ) { }
}
