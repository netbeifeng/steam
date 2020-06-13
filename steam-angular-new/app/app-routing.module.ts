import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { IndexComponent } from './index/index.component';
import { SearchComponent } from './search/search.component';


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
  },
  {
    path:'search',
    component:SearchComponent
  },
  {
    path:'search/:username',
    component:SearchComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
