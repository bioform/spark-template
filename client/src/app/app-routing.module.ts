import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CardsComponent } from './cards/cards.component';
import { CardsManagerComponent } from './cards-manager/cards-manager.component';

const routes: Routes = [
  {
    path: 'cards',
    component: CardsComponent,
    data: { title: 'Доска объявлений' }
  },
  {
    path: 'cards-manager',
    component: CardsManagerComponent,
    data: { title: 'Мои объявления' }
  },
  { path: '',
    redirectTo: '/cards',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
