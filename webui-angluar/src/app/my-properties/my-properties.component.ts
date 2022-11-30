import { Component, OnInit } from '@angular/core';
import { GlobalComponent } from '../global-component';
import { PropertyService } from '../services/property.service';

@Component({
  selector: 'app-my-properties',
  templateUrl: './my-properties.component.html',
  styleUrls: ['./my-properties.component.css']
})
export class MyPropertiesComponent implements OnInit {
  public picUrl:string = GlobalComponent.url + "images";
  page: number = 1;
  count: number = 0;
  pageSize: number = 2;

  public properties : any = [];

  constructor(private propertyService: PropertyService) { }

  ngOnInit(): void {
    this.propertyService.getUserProperties().subscribe(
      data => {
        this.properties = data;
      })
  }

  onTableDataChange(event: any) {
    this.page = event;
  }

}
