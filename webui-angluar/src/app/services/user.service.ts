import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GlobalComponent } from '../global-component';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url: string = GlobalComponent.url;

  constructor(private http: HttpClient) { }

  register(user:any): Observable<any>{
   return this.http.post<any>(this.url+'register', user)
  }

  login(user:any): Observable<any>{
    return this.http.post<any>(this.url+'login', user)  
  }

  logout(){
    this.http.get(this.url+'logout')
    sessionStorage.clear();
    
  }

  isLogged():boolean{
    return (sessionStorage.getItem("user") !== null); 

  }

  isAdmin():boolean{
    return (sessionStorage.getItem("role") === "ROLE_ADMIN"); 
  }

  getToken() {
    return sessionStorage.getItem('user');
  }

  getUsername(){
    return sessionStorage.getItem('username');
  }
}
