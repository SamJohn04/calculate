import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CalculatorComponent } from './calculator/calculator.component';

const routes: Routes = [
  {
    path: 'login',
    component: CalculatorComponent
  }, {
    path: 'signup',
    component: CalculatorComponent
  }, {
    path: 'calculate',
    component: CalculatorComponent
  },  {
    path: '**',
    redirectTo: 'login'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
