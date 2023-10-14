import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  error?: string = '';
  loading: boolean = false;

  loginForm = this.formBuilder.group({
    username: '',
    password: ''
  });

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  login() {
    this.loading = true;
    const { username, password } = this.loginForm.value;
    console.log('Login response: ', this.loginForm.value);
    this.authService.login(username!, password!).subscribe(
      (response) => {
        console.log(response);
        this.authService.setAuth({
          username: username!,
          password: password!
        });
        this.loading = false;
        this.router.navigate(['/calculate']);
      },
      (error) => {
        if (error instanceof HttpErrorResponse) {
          switch (error.status) {
            case 400:
              this.error = error.error;
              break;
            default:
              this.error = "An error occured on our end please try again later";
              break;
          }
          console.error('Login error:', error);
          this.loginForm.setValue({ username: '', password: '' });
        }
        this.loading = false;
      }
    );
  }

  @Output() onLogin = new EventEmitter<any>()
}
