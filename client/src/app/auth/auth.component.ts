import { Component, OnInit, Inject } from '@angular/core';
import { Http } from '@angular/http';

import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

import { AuthService } from 'angularx-social-login';
import { SocialUser } from 'angularx-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from 'angularx-social-login';
import { VKLoginProvider} from 'angularx-social-login-vk';

export interface DialogData {
  extra_data: object;
}

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent {
  user: SocialUser;

  constructor(private authService: AuthService, public dialog: MatDialog) {}


  openDialog(): void {
    const dialogRef = this.dialog.open(AuthDialogComponent, {
      width: '250px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.user = result;
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
export class AuthDialogComponent implements OnInit  {
  user: SocialUser;

  constructor(
    private authService: AuthService,
    private http: Http,
    public dialogRef: MatDialogRef<AuthDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}


  ngOnInit() {
    this.authService.authState.subscribe((user) => {
      this.user = user;

      this.sendAuthToken(user.idToken);
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

  sendAuthToken(token: string) : void {
     this.http.post("url to google login in your rest api",
        {
           token: token
        }
     ).subscribe(
        onSuccess => {
           this.dialogRef.close(this.user);
        }, onFail => {
           //login was unsuccessful
           //show an error message
        }
     );
  }

}
