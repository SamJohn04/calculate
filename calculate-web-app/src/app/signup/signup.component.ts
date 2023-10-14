import { Component, EventEmitter, Output } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  error?: string = '';
  loading: boolean = false;

  signupForm = this.formBuilder.group({
    username: '',
    password: ''
  });

  constructor(
    private authService: AuthService, 
    private formBuilder: FormBuilder,
    private router: Router
  ){}

  signup() {
    this.loading = true;
    const { username, password } = this.signupForm.value;
    console.log('signup response: ', this.signupForm.value);
     this.authService.signup(username!, password!).subscribe(
      (response) => {
        this.error = '';
        console.log(response);
          this.authService.setAuth({
            username: username!,
            password: password!
          });
          this.onLogin.emit({username, password})
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
          console.error('Signup error:', error);
          this.signupForm.setValue({ username: '', password: '' });
        }
        this.loading = false;
      }
    );
  }

  @Output() onLogin = new EventEmitter<any>()
}
