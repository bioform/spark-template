import { Component, OnInit, Inject } from '@angular/core';
import { Http } from '@angular/http';

import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

import { AuthService } from 'angularx-social-login';
import { SocialUser } from 'angularx-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angularx-social-login';
import { VKLoginProvider} from 'angularx-social-login-vk';

import { SessionService } from '../session.service';

export interface DialogData {
  extra_data: object;
}

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {
  user: SocialUser;

  constructor(private authService: AuthService, private sessionService: SessionService, public dialog: MatDialog) {}
  
  ngOnInit() {
    this.sessionService.authState.subscribe((user) => {
      this.user = user;
    });
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AuthDialogComponent, {
      width: '250px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      // 'result' is user
    });
  }

  signOut(): void {
    this.authService.signOut();
  }
}

@Component({
  selector: 'auth-dialog',
  templateUrl: 'auth-dialog.component.html',
  styleUrls: ['./auth-dialog.component.scss']
})
export class AuthDialogComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private sessionService: SessionService,
    private http: Http,
    public dialogRef: MatDialogRef<AuthDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}


  ngOnInit() {
    this.authService.authState.subscribe((user) => {
      if( user ){
        this.sendGoogleAuthToken(user);
      }
      this.sessionService.changeAuthState(user);
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

  signInWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID);
  }

  signInWithVk(): void {
    this.authService.signIn(VKLoginProvider.PROVIDER_ID);
    /*
      .then(userData => {
        console.log('userData:', userData);
      });
      */
  }

  sendGoogleAuthToken(user: SocialUser) : void {
     this.http.post('/api/auth/google/login',
        {
          idToken: user.idToken
        }
     ).subscribe(
        onSuccess => {
           this.dialogRef.close(user);
        }, onFail => {
           this.authService.signOut();
        }
     );
  }

}
