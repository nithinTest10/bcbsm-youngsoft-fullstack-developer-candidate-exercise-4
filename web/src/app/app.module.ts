import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FileuploadComponent } from './fileupload/fileupload.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HttpInterceptorService } from './service/httpInterceptorservice';
import {MatInputModule} from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import {DataTablesModule} from 'angular-datatables';
import { DocumentsComponent } from './documents/documents.component';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatInputModule,
    MatCardModule,
    DataTablesModule
  ],
  declarations: [
    AppComponent,
    LoginComponent,
    FileuploadComponent,
    HomeComponent,
    RegisterComponent,
    DocumentsComponent
  ],
  exports: [
  ],
  providers: [
{
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
