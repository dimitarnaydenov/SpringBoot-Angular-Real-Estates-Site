import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  private url: string = "http://localhost:8888/all"

  constructor(private http: HttpClient) { }

  addProperty(property:any){
   
    this.http.post<any>('http://localhost:8888/addProperty', property).subscribe(data => {
      console.log(data);
  })
  }

  getProperties(){
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Credentials': 'true',
                'Access-Control-Allow-Headers': 'Content-Type',
                'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE',
      'Authorization': 'Bearer ' +  window.sessionStorage.getItem("user")});
    let options = { headers: headers };

    return this.http.get(this.url,options);
    // return [
    //   {name:"Apartment", price:"500",address:"ul.131",size:"60",description:"s gledka kum Vitosha"},
    //   {name:"Apartment", price:"600",address:"bul.Tsarigradsko shose",size:"80",description:""},
    //   {name:"House", price:"1500",address:"lozen",size:"160",description:"s dva garaja"}
    // ];
  }
}
