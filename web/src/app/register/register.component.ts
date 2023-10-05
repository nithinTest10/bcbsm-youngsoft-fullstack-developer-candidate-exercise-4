import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterService } from '../service/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerForm =new FormGroup({
    userName: new FormControl('', Validators.required),
    userId: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    email: new FormControl('', Validators.required)
  });

  constructor( private router: Router, private registerService: RegisterService) {

  }

  ngOnInit(): void {
  }

  onSubmit() {
      const username = this.registerForm.value['userName'];
      const userId = this.registerForm.value['userId'];
      const password = this.registerForm.value['password'];
      const email = this.registerForm.value['userId'];
      this.registerService.addUser(String(username), String(userId), String(password), String(email));
    }
  }
