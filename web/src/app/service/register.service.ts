import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import axios from "axios";
import { environment } from "../environment";

@Injectable({
    providedIn: 'root'
})

export class RegisterService {

    constructor(private router: Router) {
    }

    addUser(username: string, userId: string, password: string, email: string) {
        const data = { "userId": userId, "userName": username, "password": password, "email": email };

        axios.put(environment.backendurl + "/user/add", data,
            environment.options).then(response => {
                alert("user added successfully");
            })
            .catch(error => {
                console.error(error);
            });
      }
}

