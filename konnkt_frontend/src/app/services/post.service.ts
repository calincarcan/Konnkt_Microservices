import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from '../models/post.interface';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = 'http://localhost:8000/backend/api/post';

  constructor(private http: HttpClient) { }

  getAllPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/getPosts`);
  }

  getPost(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/getPost/${id}`);
  }

  deletePost(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deletePost/${id}`);
  }

  createPost(post: Post): Observable<Post> {
    return this.http.post<Post>(`${this.apiUrl}/createPost`, post);
  }

  updatePostTitle(id: number, title: string): Observable<Post> {
    return this.http.put<Post>(`${this.apiUrl}/updatePost/title/${id}`, title);
  }

  updatePostContent(id: number, content: string): Observable<Post> {
    return this.http.put<Post>(`${this.apiUrl}/updatePost/content/${id}`, content);
  }
}