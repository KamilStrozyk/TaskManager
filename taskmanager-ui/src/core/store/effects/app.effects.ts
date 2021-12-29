
import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { AddTask, AddTaskList, AddTaskSpace, AppActionTypes, GetUserTaskLists, GetUserTaskListsSuccess, GetUserTasks, GetUserTaskSpaces, GetUserTaskSpacesSuccess, GetUserTasksSuccess, LoginUser, LoginUserSuccess, RegisterUser, RegisterUserSuccess, RemoveTask, RemoveTaskList, RemoveTaskSpace, UpdateTask, UpdateTaskList, UpdateTaskSpace } from '../actions/app.actions';
import { MatSnackBar } from '@angular/material/snack-bar';

import { catchError, exhaustMap, map, switchMap } from 'rxjs/operators';
import { EMPTY } from 'rxjs';
import { AuthService } from 'src/core/services/auth.service';
import { UserModel } from 'src/core/models/user';
import { Router } from '@angular/router';
import { Task } from 'src/core/models/task';
import { TaskSpace } from 'src/core/models/task-space';
import { TaskSpaceService } from 'src/core/services/task-space.service';
import { TaskListService } from 'src/core/services/task-list.service';
import { TaskList } from 'src/core/models/task-list';
import { TaskService } from 'src/core/services/task.service';

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

    getUserTasks$ = createEffect(() => this.actions$.pipe(
        ofType(GetUserTasks),
        exhaustMap((action) => this.taskService.getUserTasks(action.id)
            .pipe(
                map((tasks: Array<Task>) => GetUserTasksSuccess({ tasks: tasks })),
                catchError((message) => this.handleError(message))
            ))));

    getUserTaskLists$ = createEffect(() => this.actions$.pipe(
        ofType(GetUserTaskLists),
        exhaustMap((action) => this.taskListService.getUserTaskLists(action.id)
            .pipe(
                map((taskLists: Array<TaskList>) => GetUserTaskListsSuccess({ taskLists: taskLists })),
                catchError((message) => this.handleError(message))
            ))));

    addTaskSpace$ = createEffect(() => this.actions$.pipe(
        ofType(AddTaskSpace),
        exhaustMap((action) => this.taskSpaceService.addTaskSpace(action.taskSpace)
            .pipe(
                switchMap(async () => GetUserTaskSpaces()),
                catchError((message) => this.handleError(message))
            ))));

    addTaskList$ = createEffect(() => this.actions$.pipe(
        ofType(AddTaskList),
        exhaustMap((action) => this.taskListService.addTaskList(action.taskList)
            .pipe( 
                switchMap(async () => {window.location.reload(); return GetUserTaskSpaces()}),
                catchError((message) => this.handleError(message))
            ))));

    addTask$ = createEffect(() => this.actions$.pipe(
        ofType(AddTask),
        exhaustMap((action) => this.taskService.addTask(action.task)
            .pipe( 
                switchMap(async () => {window.location.reload(); return GetUserTaskSpaces()}),
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

    updateTaskList$ = createEffect(() => this.actions$.pipe(
        ofType(UpdateTaskList),
        exhaustMap((action) => this.taskListService.updateTaskList(action.taskList)
            .pipe(
                switchMap(async () => {window.location.reload(); return GetUserTaskSpaces()}),
                catchError((message) => this.handleError(message))
            ))));

    removeTaskList$ = createEffect(() => this.actions$.pipe(
        ofType(RemoveTaskList),
        exhaustMap((action) => this.taskListService.removeTaskList(action.id)
            .pipe(
                switchMap(async () => {window.location.reload(); return GetUserTaskSpaces()}),
                catchError((message) => this.handleError(message))
            ))));

    updateTask$ = createEffect(() => this.actions$.pipe(
        ofType(UpdateTask),
        exhaustMap((action) => this.taskService.updateTask(action.task)
            .pipe(
                switchMap(async () => {window.location.reload(); return GetUserTaskSpaces()}),
                catchError((message) => this.handleError(message))
            ))));

    removeTask$ = createEffect(() => this.actions$.pipe(
        ofType(RemoveTask),
        exhaustMap((action) => this.taskService.removeTask(action.id)
            .pipe(
                switchMap(async () => {window.location.reload(); return GetUserTaskSpaces()}),
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
        private taskService: TaskService,
        private taskListService: TaskListService,
        private snackbar: MatSnackBar,
        private router: Router
    ) { }
}
