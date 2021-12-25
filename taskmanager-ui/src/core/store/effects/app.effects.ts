
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { AddTaskSpace, AppActionTypes, GetUserTaskSpaces, GetUserTaskSpacesSuccess, LoginUser, LoginUserSuccess, RegisterUser, RegisterUserSuccess, RemoveTaskSpace, UpdateTaskSpace } from '../actions/app.actions';
import { MatSnackBar } from '@angular/material/snack-bar';

import { catchError, exhaustMap, map, switchMap } from 'rxjs/operators';
import { EMPTY } from 'rxjs';
import { AuthService } from 'src/core/services/auth.service';
import { UserModel } from 'src/core/models/user';
import { Router } from '@angular/router';
import { TaskSpace } from 'src/core/models/task-space';
import { TaskSpaceService } from 'src/core/services/task-space.service';

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

    getUserTaskSpaces$ = createEffect(() => this.actions$.pipe(
        ofType(GetUserTaskSpaces),
        exhaustMap((action) => this.taskSpaceService.getUserTaskSpaces()
            .pipe(
                map((taskSpaces: Array<TaskSpace>) => GetUserTaskSpacesSuccess({ taskSpaces: taskSpaces })),
                catchError((message) => this.handleError(message))
            ))));

    addTaskSpace$ = createEffect(() => this.actions$.pipe(
        ofType(AddTaskSpace),
        exhaustMap((action) => this.taskSpaceService.addTaskSpace(action.taskSpace)
            .pipe(
                switchMap(async () => GetUserTaskSpaces()),
                catchError((message) => this.handleError(message))
            ))));

    updateTaskSpace$ = createEffect(() => this.actions$.pipe(
        ofType(UpdateTaskSpace),
        exhaustMap((action) => this.taskSpaceService.updateTaskSpace(action.taskSpace)
            .pipe(
                switchMap(async () => GetUserTaskSpaces()),
                catchError((message) => this.handleError(message))
            ))));

    removeTaskSpace$ = createEffect(() => this.actions$.pipe(
        ofType(RemoveTaskSpace),
        exhaustMap((action) => this.taskSpaceService.removeTaskSpace(action.id)
            .pipe(
                switchMap(async () => GetUserTaskSpaces()),
                catchError((message) => this.handleError(message))
            ))));

    handleError = (message) => {
        this.snackbar.open(message.message, '', {
            duration: 3000,
            panelClass: ['mat-toolbar', 'mat-warn']
        }); return EMPTY
    }

    constructor(
        private actions$: Actions,
        private authService: AuthService,
        private taskSpaceService: TaskSpaceService,
        private snackbar: MatSnackBar,
        private router: Router
    ) { }
}
