import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  loggedIn: boolean = false;
  isAdmin: boolean = false;

  constructor() {
    const token = localStorage.getItem('token');
    console.log("jwt: ", token);
    if (token) {
      this.loggedIn = true;
      const payload = JSON.parse(atob(token.split('.')[1]));
      if (payload.roles && Array.isArray(payload.roles) && payload.roles.includes('admin')) {
        this.isAdmin = true;
      }
    }
  }

  register(): void {
    window.location.href = '/register';
  }

  login(): void {
    window.location.href = '/login';
  }

  logout(): void {
    localStorage.removeItem('token');
    this.loggedIn = false;
    window.location.href = '/home';
  }


}
