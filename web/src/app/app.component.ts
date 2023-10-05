import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Docstest';
  user: string;
  constructor( private router: Router, private authenticationService : AuthenticationService) {

  }

  logout() 
  {
    this.authenticationService.logout();
  }

  isUserLoggedIn() {
    this.user = this.authenticationService.getLoggedInUserId();
    return this.authenticationService.isUserLoggedIn();
  }
}
