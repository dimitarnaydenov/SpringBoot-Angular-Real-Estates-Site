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
  page: number = 1;
  count: number = 0;
  tableSize: number = 3;

  constructor(private propertyService: PropertyService, private http: HttpClient) { }

  ngOnInit(): void {
    //this.properties = this.propertyService.getProperties().subscribe(data => this.properties = data);
      this.properties = [
        {id:1,name:"Apartment", price:"500",address:"ul.131",size:"60",description:"s gledka kum Vitosha"},
        {id:2,name:"Apartment", price:"600",address:"bul.Tsarigradsko shose",size:"80",description:""},
        {id:3,name:"House", price:"1500",address:"lozen",size:"160",description:"s dva garaja"},
        {id:4,name:"Garage", price:"100",address:"bul.Vitosha",size:"20",description:"s gledka kum Vitosha"},
        {id:5,name:"Apartment", price:"600",address:"bul.Tsarigradsko shose",size:"80",description:""},
        {id:6,name:"House", price:"1500",address:"lozen",size:"160",description:"s dva garaja"},
        {id:7,name:"Apartment", price:"760",address:"bul. Stamobliski",size:"60",description:"s gledka kum Vitosha"},
        {id:8,name:"Apartment", price:"600",address:"bul.Tsarigradsko shose",size:"80",description:""},
        {id:9,name:"House", price:"1500",address:"lozen",size:"160",description:"s dva garaja"}
     ];
  }

  onClickSubmit(data:any) {

    this.propertyService.addProperty({"name":data.name,"price":data.price,"address":data.address,"description":data.description})
    
 }

 onTableDataChange(event: any) {
  this.page = event;
}

}
