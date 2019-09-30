import { Injectable, Output, EventEmitter } from '@angular/core';
import { SocialUser } from 'angularx-social-login';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  user: SocialUser;

  @Output() authState: EventEmitter<SocialUser> = new EventEmitter();

  constructor() { }

  changeAuthState(user: SocialUser) {
    this.user = user;
    this.authState.emit(user);
  }
}
