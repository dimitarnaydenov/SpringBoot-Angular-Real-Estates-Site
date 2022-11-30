import { Component, OnInit } from '@angular/core';
import { PropertyService } from '../services/property.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { GlobalComponent } from '../global-component';

@Component({
  selector: 'app-properties',
  templateUrl: './properties.component.html',
  styleUrls: ['./properties.component.css']
})
export class PropertiesComponent implements OnInit {

  public picUrl:string = GlobalComponent.url + "images";
  public properties : any = [];
  page: number = 1;
  count: number = 0;
  pageSize: number = 2;

  public search = "";

  constructor(private propertyService: PropertyService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.router.routeReuseStrategy.shouldReuseRoute = () => { return false; };


    this.route.queryParams.subscribe((params) => {
      if(params?.['search'] !== undefined)
      this.search = params?.['search']
  })

  if(this.search !== ""){
    this.propertyService.searchProperties(this.search).subscribe(
      data => {
        this.properties = data;
      })
    
   }
   else{
    this.retrieveProperties();
   }
   

    //this.properties = this.propertyService.getProperties().subscribe(data => this.properties = data)
    
    //   this.properties = [
    //     {id:1,name:"Apartment", price:"500",address:"ul.131",area:"60",description:"s gledka kum Vitosha"},
    //     {id:2,name:"Apartment", price:"600",address:"bul.Tsarigradsko shose",area:"80",description:""},
    //     {id:3,name:"House", price:"1500",address:"lozen",area:"160",description:"s dva garaja"},
    //     {id:4,name:"Garage", price:"100",address:"bul.Vitosha",area:"20",description:"s gledka kum Vitosha"},
    //     {id:5,name:"Apartment", price:"600",address:"bul.Tsarigradsko shose",area:"80",description:""},
    //     {id:6,name:"House", price:"1500",address:"lozen",area:"160",description:"s dva garaja"},
    //     {id:7,name:"Apartment", price:"760",address:"bul. Stamobliski",area:"60",description:"s gledka kum Vitosha"},
    //     {id:8,name:"Apartment", price:"600",address:"bul.Tsarigradsko shose",area:"80",description:""},
    //     {id:9,name:"House", price:"1500",address:"lozen",area:"160",description:"s dva garaja"}
    //  ];

     
  }

 onTableDataChange(event: any) {
  this.page = event;
  if(this.search === "")
    this.retrieveProperties();
}

retrieveProperties(): void {
  const params = {page:this.page-1, size:this.pageSize}

  this.propertyService.getProperties(params).subscribe({
    next:data =>{
      const { properties, totalItems } = data;
        this.properties = properties;
        this.count = totalItems;
    }});
}

}

