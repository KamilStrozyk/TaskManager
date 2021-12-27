import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { TaskList } from '../models/task-list';

@Injectable({
  providedIn: 'root'
})
export class TaskListService {
  constructor(private _httpClient: HttpClient) { }

  public getUserTaskLists(id: any): Observable<TaskList[]> {
    return this._httpClient.get(environment.odata.apiUrl + '/task-list/', {params: {spaceId: id}}).pipe(
      map((response: any) => { return response; })
    );
  }

  public removeTaskList(id : any) {
    return this._httpClient.delete(environment.odata.apiUrl + '/task-list/remove/'+ id).pipe(
      map((response: any) => { return response; })
    );
  }

  public addTaskList(taskList: TaskList){
    return this._httpClient.post(environment.odata.apiUrl + '/task-list/create', taskList).pipe(
      map((response: any) => { return response; })
    );
  }

  public updateTaskList(taskList: TaskList){
    return this._httpClient.put(environment.odata.apiUrl + '/task-list/update', taskList).pipe(
      map((response: any) => { return response; })
    );
  }

}

