import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MainNavComponent } from './main-nav/main-nav.component';
import { LayoutModule } from '@angular/cdk/layout';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule, MatInputModule, MatGridListModule, MatCardModule, MatMenuModule, MatDialogModule } from '@angular/material';

import { SearchFormComponent } from './search-form/search-form.component';
import { CardsComponent } from './cards/cards.component';
import { CardsManagerComponent } from './cards-manager/cards-manager.component';

import { SocialLoginModule, AuthServiceConfig } from "angularx-social-login";
import { GoogleLoginProvider, FacebookLoginProvider } from "angularx-social-login";
import { VKLoginProvider} from 'angularx-social-login-vk';
import { AuthComponent, AuthDialogComponent } from './auth/auth.component';

let config = new AuthServiceConfig([
  {
    id: GoogleLoginProvider.PROVIDER_ID,
    provider: new GoogleLoginProvider("527133725719-335qctmbfm4pio24s13tp48o65lg0ml0.apps.googleusercontent.com")
  },
  /*
  {
    id: FacebookLoginProvider.PROVIDER_ID,
    provider: new FacebookLoginProvider('561602290896109')
  },
  {
    id: VKLoginProvider.PROVIDER_ID,
    provider: new VKLoginProvider('VK-App-Id') // ID приложения в Вконтакте
  }
  */
]);

export function provideConfig() {
  return config;
}


@NgModule({
  declarations: [
    AppComponent,
    MainNavComponent,
    SearchFormComponent,
    CardsComponent,
    CardsManagerComponent,
    AuthComponent,
    AuthDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    BrowserAnimationsModule,
    LayoutModule,
    FormsModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatInputModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatDialogModule,
    SocialLoginModule
  ],
  entryComponents: [AuthDialogComponent],
  providers: [
    {
      provide: AuthServiceConfig,
      useFactory: provideConfig
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
