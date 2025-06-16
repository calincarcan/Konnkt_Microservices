import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './../auth.service';

@Component({
    selector: 'app-login',
    standalone: false,
    templateUrl: './login.component.html'
})
export class LoginComponent {
  form: FormGroup;
  loginError = false;

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.group({ loginInfo: '', password: '' });
  }

  onSubmit(): void {
    this.auth.login(this.form.value).subscribe({
      next: () => {
        this.router.navigate(['/']);
        console.log("login page ok");
        this.loginError = false;
      },
      error: () => {
        console.log("login page error");
        this.loginError = true;
      }
    });
  }
}
