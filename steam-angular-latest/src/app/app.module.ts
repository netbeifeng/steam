import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';  
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { IndexComponent } from './index/index.component';
import { LoginComponent } from './login/login.component';
import { SeriesService } from './series-service.service';
import { Card } from './index/card/card';
import { SearchComponent } from './search/search.component';
@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    LoginComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [SeriesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
