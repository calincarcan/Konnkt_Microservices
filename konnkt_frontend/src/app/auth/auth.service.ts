import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  // private apiUrl = 'http://localhost:8091/api/auth';
  private apiUrl = 'http://localhost:8000/auth/api/auth';
  constructor(private http: HttpClient, private router: Router) {}

  register(data: { email: string, password: string, firstName: string, lastName: string }): Observable<string> {
    return this.http.post(`${this.apiUrl}/register`, data, { responseType: 'text' })
      .pipe(
        tap({
          next: (response: string) => {
            console.log('Registration successful:', response);
          },
          error: (error) => {
            console.error('Registration error:', error);
            throw error;
          }
        })
      );
  }

  login(credentials: { loginInfo: string, password: string }): Observable<string> {
    console.log('Sending credentials:', credentials);
    return this.http.post<string>(`${this.apiUrl}/login`, credentials, { responseType: 'text' as 'json' })
      .pipe(
        tap({
          next: (token: string) => {
            // The response is directly the JWT string from Spring
            console.log('Received token:');
            if (token) {
              localStorage.setItem('token', token);
              console.log('Token stored successfully');
            } else {
              throw new Error('No token received');
            }
          },
          error: (error) => {
            console.error('Login error in service:', error);
            localStorage.removeItem('token'); // Clean up just in case
            throw error;
          }
        })
      );
  }

  logout(): void {
    localStorage.removeItem("token");
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem("token");
  }

  getToken(): string | null {
    return localStorage.getItem("token");
  }
}
