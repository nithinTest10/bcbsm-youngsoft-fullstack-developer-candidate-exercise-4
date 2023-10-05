import { Injectable } from '@angular/core';
import axios from 'axios';
import { environment } from '../environment';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    public username: string = "";
    public password: string = "";
    constructor(private router: Router) {
    }

    login(username: string, password: string){

        let headers =  {
            "Content-Type": 'application/json',
            "Access-Control-Allow-Origin": '*',
        };

        const data = { "loginId": username, "loginPassword": password };
        axios.post(environment.backendurl + "/usersession/signin", data,
        {"headers":headers}).then(response => {
                // Handle successful response
                console.log(response.data);
                sessionStorage.setItem(environment.SESSION_ATTRIBUTE_NAME, response.data.sessionId);
                sessionStorage.setItem(environment.LOGGEDUSER, username);
                this.router.navigate(['/home']);
                return true;
            })
            .catch(error => {
                console.error(error);
                return false;
            });
    }

    logout() {
        let headers =  {
            "Content-Type": 'application/json',
            "Access-Control-Allow-Origin": '*',
            "Authorization" : sessionStorage.getItem(environment.SESSION_ATTRIBUTE_NAME),
            "userId": sessionStorage.getItem(environment.LOGGEDUSER)
        };

        axios.post(environment.backendurl + "/usersession/signout", '',
        {"headers":headers}).then(response => {
                // Handle successful response
                console.log(response.data);
                return true;
            })
            .catch(error => {
                console.error(error);
                return false;
            });


        sessionStorage.clear();
        this.username = "";
        this.router.navigate(['login']);
    }

    validate() {
        let headers =  {
            "Content-Type": 'application/json',
            "Access-Control-Allow-Origin": '*',
            "Authorization" : sessionStorage.getItem(environment.SESSION_ATTRIBUTE_NAME),
            "userId": sessionStorage.getItem(environment.LOGGEDUSER)
        };

        axios.post(environment.backendurl + "/usersession/validatesession", '',
        {"headers":headers}).then(response => {
                // Handle successful response
                console.log(response.data);
            })
            .catch(error => {
                console.error(error);
                alert("session expired");
                sessionStorage.clear();
                this.username = "";
                this.router.navigate(['login']);
                return false;
            });

    }

    isUserLoggedIn() {
        let sessionId = sessionStorage.getItem(environment.SESSION_ATTRIBUTE_NAME)
        if (sessionId === null) return false
        return true
    }

    getLoggedInUserId() {
        let user = sessionStorage.getItem(environment.LOGGEDUSER)
        if (user === null) return ''
        return user
    }
}