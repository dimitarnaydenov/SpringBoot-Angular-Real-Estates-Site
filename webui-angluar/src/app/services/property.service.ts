import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GlobalComponent } from '../global-component';
import { UserService } from './user.service';


@Injectable({
  providedIn: 'root'
})
export class PropertyService {
 
  private url: string = GlobalComponent.url;

  constructor(private http: HttpClient, private userService: UserService) { }

  addProperty(data:any, image1:any,image2:any){
  const formData: FormData = new FormData();

  let images = [image1,image2]

  formData.append('type', data.type);
  formData.append('address', data.address);
  formData.append('price', data.price);
  formData.append('description', data.description);
  formData.append('area', data.area);
  for (var x = 0; x < images.length; x++) {
    formData.append('images[]', images[x]);
  }
  

  return this.http.post<any>(this.url+'addProperty', formData)
  }

  getPropertyById(id:string){
    
  //   let properties = [
  //     {id:1,name:"Apartment", price:"500",address:"ul.131",size:"60",description:"s gledka kum Vitosha"},
  //     {id:2,name:"Apartment", price:"600",address:"bul.Tsarigradsko shose",size:"80",description:""},
  //     {id:3,name:"House", price:"1500",address:"lozen",size:"160",description:"s dva garaja"}
  //  ];

   //return properties.find(p => p.id === parseInt(id))
   return this.http.get<any>(this.url+`getProperty?id=${id}`)
  }

  getProperties(params?:any):Observable<any>{
    return this.http.get(this.url+'all',{params});
    // return [
    //   {name:"Apartment", price:"500",address:"ul.131",size:"60",description:"s gledka kum Vitosha"},
    //   {name:"Apartment", price:"600",address:"bul.Tsarigradsko shose",size:"80",description:""},
    //   {name:"House", price:"1500",address:"lozen",size:"160",description:"s dva garaja"}
    // ];
  }

  getUserProperties(){
    return this.http.get(this.url+'getUserProperties')
  }

  remove(id: string) {
    return this.http.delete(this.url+`removeProperty?id=${id}`)
  }

  searchProperties(search:string){
    return this.http.get(this.url+`search?search=${search}`)
  }
}
