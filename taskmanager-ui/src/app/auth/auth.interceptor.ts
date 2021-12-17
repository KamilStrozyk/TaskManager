import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { IAppState } from 'src/core/store/state/app.state';
import { Store } from '@ngrx/store';
import { LogoutUser } from 'src/core/store/actions/app.actions';
import { UserModel } from 'src/core/models/user';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private router: Router, private store: Store<IAppState>) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (localStorage.getItem('user')) {
            const user: UserModel = JSON.parse(localStorage.getItem('user'));
            const clonedReq = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${user.token}`
                }
            });
            return next.handle(clonedReq).pipe(
                tap(
                    succ => {
                    },
                    err => {
                        if (err.status == 401) {
                            localStorage.removeItem('user');
                            this.store.dispatch(LogoutUser());
                            this.router.navigateByUrl('/login');
                        }
                    }
                )
            )
        }
        else {
            return next.handle(req.clone());
        }
    }
}