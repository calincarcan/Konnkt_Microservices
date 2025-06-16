import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Forum } from '../models/forum.interface';

@Injectable({
  providedIn: 'root'
})
export class ForumService {
  private apiUrl = 'http://localhost:8000/backend/api/forum';

  constructor(private http: HttpClient) { }

  getAllForums(): Observable<Forum[]> {
    return this.http.get<Forum[]>(`${this.apiUrl}/getForums`);
  }

  getForum(id: number): Observable<Forum> {
    return this.http.get<Forum>(`${this.apiUrl}/getForum/${id}`);
  }

  createForum(forum: Forum): Observable<Forum> {
    return this.http.post<Forum>(`${this.apiUrl}/createForum`, forum);
  }

  updateForumName(id: number, name: string): Observable<Forum> {
    return this.http.put<Forum>(`${this.apiUrl}/updateForum/name/${id}`, name);
  }

  updateForumDescription(id: number, description: string): Observable<Forum> {
    return this.http.put<Forum>(`${this.apiUrl}/updateForum/desc/${id}`, description);
  }

  deleteForum(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteForum/${id}`);
  }
}
