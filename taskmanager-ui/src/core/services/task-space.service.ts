import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { TaskSpace } from '../models/task-space';

@Injectable({
  providedIn: 'root'
})
export class TaskSpaceService {
  constructor(private _httpClient: HttpClient) { }

  public getUserTaskSpaces(): Observable<TaskSpace[]> {
    return this._httpClient.get(environment.odata.apiUrl + '/task-space/').pipe(
      map((response: any) => { return response; })
    );
  }

  public addTaskSpace(taskSpace: TaskSpace){
    return this._httpClient.post(environment.odata.apiUrl + '/task-space/create', taskSpace).pipe(
      map((response: any) => { return response; })
    );
  }

}

