import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { IndexComponent } from './index/index.component';


const routes: Routes = [
  {
    path:'',
    component:LoginComponent
  },
  {
    path:'index',
    component:IndexComponent
  },
  {
    path:'index/:username',
    component:IndexComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
