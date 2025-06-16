import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vote } from '../models/vote.interface';

@Injectable({
  providedIn: 'root'
})
export class VoteService {
  // private apiUrl = 'http://localhost:8092/api/vote';
  private apiUrl = 'http://localhost:8000/backend/api/vote';

  constructor(private http: HttpClient) { }

  getAllVotes(): Observable<Vote[]> {
    return this.http.get<Vote[]>(`${this.apiUrl}/getVotes`);
  }

  getVote(id: number): Observable<Vote> {
    return this.http.get<Vote>(`${this.apiUrl}/getVote/${id}`);
  }

  deleteVote(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteVote/${id}`);
  }

  createVote(vote: Vote): Observable<Vote> {
    return this.http.post<Vote>(`${this.apiUrl}/createVote`, vote);
  }

  changeVote(id: number): Observable<Vote> {
    return this.http.put<Vote>(`${this.apiUrl}/changeVote/${id}`, {});
  }
}