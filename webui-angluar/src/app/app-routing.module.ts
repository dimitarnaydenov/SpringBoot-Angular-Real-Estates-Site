import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddPropertyComponent } from './add-property/add-property.component';
import { EditPropertyComponent } from './edit-property/edit-property.component';
import { AuthGuard } from './helpers/auth/auth.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { MyPropertiesComponent } from './my-properties/my-properties.component';
import { PropertiesComponent } from './properties/properties.component';
import { PropertyItemComponent } from './property-item/property-item.component';
import { RegistrationComponent } from './registration/registration.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {path:'home',component: HomeComponent},
  {path:'login',component: LoginComponent},
  {path:'register',component: RegistrationComponent},
  {path:'properties',component: PropertiesComponent},
  {path:'property/:id',component: PropertyItemComponent},
  {path:'addProperty', component: AddPropertyComponent, canActivate:[AuthGuard]},
  {path:'editProperty/:id',component: EditPropertyComponent, canActivate:[AuthGuard]},
  {path:'myProperties',component: MyPropertiesComponent, canActivate:[AuthGuard]}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
