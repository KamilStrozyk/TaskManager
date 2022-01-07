import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Task } from '../models/task';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  constructor(private _httpClient: HttpClient) { }

  public getUserTasks(id: any): Observable<Task[]> {
    return this._httpClient.get(environment.odata.apiUrl + '/task/', {params: {listId: id}}).pipe(
      map((response: any) => { return response; })
    );
  }

  public removeTask(id : any) {
    return this._httpClient.delete(environment.odata.apiUrl + '/task/remove/'+ id).pipe(
      map((response: any) => { return response; })
    );
  }

  public addTask(task: Task){
    return this._httpClient.post(environment.odata.apiUrl + '/task/create', task).pipe(
      map((response: any) => { return response; })
    );
  }

  public updateTask(task: Task){
    return this._httpClient.put(environment.odata.apiUrl + '/task/update', task).pipe(
      map((response: any) => { return response; })
    );
  }

}

