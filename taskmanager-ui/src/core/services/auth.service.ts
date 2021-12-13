import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { UserModel, Credentials, RegisterUser } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private _httpClient: HttpClient) { }

  public loginUser(credentials: Credentials): Observable<UserModel> {
    return this._httpClient.post(environment.odata.apiUrl + '/auth/signup', credentials).pipe(
      map((response: any) => { return response; })
    );
  }

  public registerUser(credentials: RegisterUser): Observable<string> {
    return this._httpClient.post(environment.odata.apiUrl + '/auth/signin', credentials).pipe(
      map((response: any) => { return response; })
    );
  }
}

