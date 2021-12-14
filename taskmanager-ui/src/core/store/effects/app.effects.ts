
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { AppActionTypes, LoginUser, LoginUserSuccess, RegisterUser, RegisterUserSuccess } from '../actions/app.actions';
import { MatSnackBar } from '@angular/material/snack-bar';

import { catchError, exhaustMap, map, switchMap } from 'rxjs/operators';
import { EMPTY } from 'rxjs';
import { AuthService } from 'src/core/services/auth.service';
import { UserModel } from 'src/core/models/user';
import { Router } from '@angular/router';

@Injectable()
export class AppEffects {

    loginUser$ = createEffect(() => this.actions$.pipe(
        ofType(LoginUser),
        exhaustMap((action) => this.authService.loginUser(action.credentials)
            .pipe(
                map((user: UserModel) => {
                    this.router.navigate(['/']);
                    localStorage.setItem('user', JSON.stringify(user));
                    return LoginUserSuccess({ user: user })
                }),
                catchError(() => EMPTY)
            ))));

    registerUser$ = createEffect(() => this.actions$.pipe(
        ofType(RegisterUser),
        exhaustMap((action) => this.authService.registerUser(action.user)
            .pipe(
                map((message) => {
                    this.router.navigate(['/login']);
                    return RegisterUserSuccess()
                }),
                catchError((message) => this.handleError(message)),
            ))));

    logoutUser$ = createEffect(() => this.actions$.pipe(
        ofType(AppActionTypes.LogoutUser),
        switchMap(async () => {
            localStorage.removeItem('user');
            this.router.navigate(['/login']);
            return LoginUserSuccess({ user: undefined })
        }),
        catchError(() => EMPTY),
    ));

    handleError = (message) => {
        this.snackbar.open(message.message, '', {
            duration: 3000,
            panelClass: ['mat-toolbar', 'mat-warn']
        }); return EMPTY
    }

    constructor(
        private actions$: Actions,
        private authService: AuthService,
        private snackbar: MatSnackBar,
        private router: Router
    ) { }
}
