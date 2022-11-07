import { Component, OnInit } from '@angular/core';
import { PropertyService } from '../property.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-properties',
  templateUrl: './properties.component.html',
  styleUrls: ['./properties.component.css']
})
export class PropertiesComponent implements OnInit {

  public properties : any = [];

  constructor(private propertyService: PropertyService, private http: HttpClient) { }

  ngOnInit(): void {
    this.properties = this.propertyService.getProperties().subscribe(data => this.properties = data);
  }

  onClickSubmit(data:any) {

    this.propertyService.addProperty({"name":data.name,"price":data.price,"address":data.address,"description":data.description})
    
 }

 logout(){
  this.http.get("http://localhost:8888/logout");
 }

}
