import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url: string = "http://localhost:8888/"

  constructor(private http: HttpClient) { }

  register(user:any){
    this.http.post<any>(this.url+'register', user).subscribe(data => {
      console.log(data);
  })
  }

  login(user:any){
    this.http.post<any>(this.url+'login', user).subscribe(data => {
      console.log(data);
      window.sessionStorage.setItem('user', data['accessToken']);
  })
  }

  logout(){
    
  }

  getToken() {
    return localStorage.getItem('user');
  }
}
