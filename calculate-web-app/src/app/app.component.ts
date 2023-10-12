import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  username = 'Samton';
  password = undefined;
  email = undefined;
  onOutletLoaded(component: any) {
    component.username = this.username;
  }
}
