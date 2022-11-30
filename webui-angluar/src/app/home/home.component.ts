import { Component, OnInit } from '@angular/core';
import { GlobalComponent } from '../global-component';
import { PropertyService } from '../services/property.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public url: string = GlobalComponent.url;

  public properties: any  = [];

  constructor(private propertyService:PropertyService) { }

  ngOnInit(): void {
    const params = {page:0, size:2}

  this.propertyService.getProperties(params).subscribe({
    next:data =>{
      const { properties, totalItems } = data;
        this.properties = properties;
    }});

    
  }

}
