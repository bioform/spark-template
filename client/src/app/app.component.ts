import { Component } from '@angular/core';
import { MatIconRegistry } from '@angular/material';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  public constructor (public matIconRegistry: MatIconRegistry) {
      //add custom material icons
      matIconRegistry.registerFontClassAlias ('fa');
  }
}
