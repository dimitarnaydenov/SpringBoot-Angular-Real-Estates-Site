import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GlobalComponent } from '../global-component';
import { PropertyService } from '../services/property.service';

@Component({
  selector: 'app-edit-property',
  templateUrl: './edit-property.component.html',
  styleUrls: ['./edit-property.component.css']
})
export class EditPropertyComponent implements OnInit {

  public id: string = ''

  public url: string = GlobalComponent.url;

  public property: any = null;

  public photosToRemove:string[] = [];

  public showinput:boolean[] = [true,true];

  file1!: File;
  file2!: File;

  constructor(private propertyService:PropertyService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.id = params?.['id']
  })

  this.propertyService.getPropertyById(this.id).subscribe({next:(data)=>{
    this.property = data;
    
    let i = 0;
    for (const photo of this.property.photos) {
      photo.url = this.url + "images/" + this.id + '/' + photo.url;
      this.showinput[i++] = false;
    }
  }});

  }

  editPropertyOnSubmit(data:any){
    this.propertyService.editProperty(this.id,data,this.photosToRemove,this.file1,this.file2).subscribe({
      next: data => {
         this.router.navigate([`/property/${this.id}`]) 
        }});
    
  }

  deleteImage(id:string,divNum:number){
    this.photosToRemove.push(id);
    this.showinput[divNum] = true;
  }

  fileChange1(event: any) {
    if(event.target.files && event.target.files.length > 0) {
      this.file1 = event.target.files[0];
    }
  }

  fileChange2(event: any) {
    if(event.target.files && event.target.files.length > 0) {
      this.file2 = event.target.files[0];
    }
  }

}
