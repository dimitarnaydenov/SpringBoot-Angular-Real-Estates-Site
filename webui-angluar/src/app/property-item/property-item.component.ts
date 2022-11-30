import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'
import { GlobalComponent } from '../global-component';
import { PropertyService } from '../services/property.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-property-item',
  templateUrl: './property-item.component.html',
  styleUrls: ['./property-item.component.css']
})
export class PropertyItemComponent implements OnInit {

  public id: string = ''

  private url: string = GlobalComponent.url;

  public property: any = null;

  public isCreator = false;

  public isAdmin = false;

  constructor(private propertyService: PropertyService, private userService: UserService, private route: ActivatedRoute, private router: Router) {
    this.property = {id:1,name:"Apartment", price:"500",address:"ul.131",area:"60",description:"s gledka kum Vitosha"};
  }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
        this.id = params?.['id']
    })

    this.propertyService.getPropertyById(this.id).subscribe({next:(data)=>{
      this.property = data;
      
      this.isCreator = this.property.customUser.username === this.userService.getUsername();

      for (const photo of this.property.photos) {
        photo.url = this.url + "images/" + this.id + '/' + photo.url;
      }
    }});

    if(this.userService.isLogged()){

      this.isAdmin = this.userService.isAdmin();
    }
  }

  delete(){
    if(this.isAdmin || this.isCreator)
    this.propertyService.remove(this.id).subscribe({
      next: data => {
        this.router.navigate(['/properties']) 
       }})

  }

}
