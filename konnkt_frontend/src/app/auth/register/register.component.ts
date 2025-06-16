import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from './../auth.service';
import { Router } from '@angular/router';

@Component({  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html'
})
export class RegisterComponent {
    
  form: FormGroup;
  error = false;

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.group({
        username: [''],
        email: [''],
        password: ['']
    });
  }

  onSubmit(): void {
    const data = { ...this.form.value };
    console.log(data);

    this.auth.register(data).subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => {
        this.error = true;
        console.error('Registration error');
      }
    });
  }
}