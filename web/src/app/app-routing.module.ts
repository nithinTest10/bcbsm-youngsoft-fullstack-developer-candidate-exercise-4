import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { FileuploadComponent } from './fileupload/fileupload.component';
import { isAuthGuard } from './authentication.guard';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { DocumentsComponent } from './documents/documents.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent, canActivate: [isAuthGuard] },
  { path: 'docs', component: DocumentsComponent, canActivate: [isAuthGuard] },
  { path: 'fileupload', component: FileuploadComponent, canActivate: [isAuthGuard]},
  { path: '', redirectTo: '/login', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
