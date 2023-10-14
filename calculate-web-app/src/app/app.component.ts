import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  username?: string;
  password?: string;
  onOutletLoaded(component: any) {
    component.onLogin?.subscribe((data: {username: string, password: string}) => {
      console.log('onLogin: ', data);
      this.username = data.username;
      this.password = data.password;
    })
    component.username = this.username;
  }
}
