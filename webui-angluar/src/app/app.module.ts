import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PropertiesComponent } from './properties/properties.component';
import { PropertyService } from './services/property.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule } from 'ngx-bootstrap/modal';
import { HomeComponent } from './home/home.component';
import { NavigationComponent } from './navigation/navigation.component';
import { PropertyItemComponent } from './property-item/property-item.component';
import { HttpRequestInterceptor } from './helpers/http.interceptor';
import { FooterComponent } from './footer/footer.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { AddPropertyComponent } from './add-property/add-property.component';
import { MyPropertiesComponent } from './my-properties/my-properties.component';
import { AuthGuard } from './helpers/auth/auth.guard';
import { EditPropertyComponent } from './edit-property/edit-property.component';


@NgModule({
  declarations: [
    AppComponent,
    PropertiesComponent,
    LoginComponent,
    RegistrationComponent,
    HomeComponent,
    NavigationComponent,
    PropertyItemComponent,
    FooterComponent,
    AddPropertyComponent,
    MyPropertiesComponent,
    EditPropertyComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BsDropdownModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    NgxPaginationModule
  ],
  providers: [PropertyService, {provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true }, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
