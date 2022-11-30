import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PropertyService } from '../services/property.service';

@Component({
  selector: 'app-add-property',
  templateUrl: './add-property.component.html',
  styleUrls: ['./add-property.component.css']
})
export class AddPropertyComponent implements OnInit {

  private url: string = "http://localhost:8888/"

  file1!: File;
  file2!: File;

  constructor(private http: HttpClient, private propertyService:PropertyService, private router: Router) { }

  ngOnInit(): void {
  }

  addPropertyOnSubmit(data:any){
    this.propertyService.addProperty(data,this.file1,this.file2).subscribe({
        next: data => {
          this.router.navigate(['/properties']) 
         }});
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
