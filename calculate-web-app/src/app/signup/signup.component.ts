import { Component } from '@angular/core';
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
          this.loading = false;
          this.router.navigate(['/calculate']);
      },
      (error) => {
        if (error instanceof HttpErrorResponse) {
          // this.signupForm.setValue({username: '', password: ''});
          console.error('signup error:', error.error);
          this.error = error.error;
        }
        this.loading = false;
        // Handle login error (e.g., display an error message)
      }
    );
  }
}
