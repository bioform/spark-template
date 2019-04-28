import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';

import { AuthService } from 'angularx-social-login';
import { SocialUser } from 'angularx-social-login';
import { GoogleLoginProvider, FacebookLoginProvider} from 'angularx-social-login';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  user: SocialUser;

  constructor(private authService: AuthService, private http: Http) {}

  ngOnInit() {
    this.authService.authState.subscribe((user) => {
      this.user = user;

      this.sendAuthToken(user.idToken);
    });
  }

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

  signOut(): void {
    this.authService.signOut();
  }

  sendAuthToken(token: string) : void {
     this.http.post("url to google login in your rest api",
        {
           token: token
        }
     ).subscribe(
        onSuccess => {
           //login was successful
           //save the token that you got from your REST API in your preferred location i.e. as a Cookie or LocalStorage as you do with normal login
        }, onFail => {
           //login was unsuccessful
           //show an error message
        }
     );
  }
}
