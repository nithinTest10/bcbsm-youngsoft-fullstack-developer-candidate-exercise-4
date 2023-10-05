import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, FormsModule } from '@angular/forms';
import { Router } from '@angular/router'
import { AuthenticationService } from '../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  loginForm = new FormGroup({
    userId: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });

  constructor( private router: Router, private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    sessionStorage.clear();
  }

  onSubmit() {
    const username = this.loginForm.value['userId'];
    const password = this.loginForm.value['password'];
    let success = this.authenticationService.login(String(username), String(password));

    console.log(success);
  }
}