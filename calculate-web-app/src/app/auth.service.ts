import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { User } from 'src/typing/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authenticatedUser: User | null = null;

  constructor(private http: HttpClient) {}

  authUpdate:EventEmitter<User | null> = new EventEmitter();

  signup(username: string, password: string) {
    return this.http.post('http://localhost:9000/api/v1/auth/register', {username, password}, {responseType: 'text'});
  }

  login(username: string, password: string) {
    return this.http.post('http://localhost:9000/api/v1/auth/login', {username, password}, {responseType: 'text'});
  }

  logout() {
    this.authenticatedUser = null;
    localStorage.removeItem('user');
  }

  getAuth() {
    return this.authenticatedUser;
  }

  setAuth(user: User) {
    this.authenticatedUser = user;
    this.authUpdate.emit(user);
  }
}
