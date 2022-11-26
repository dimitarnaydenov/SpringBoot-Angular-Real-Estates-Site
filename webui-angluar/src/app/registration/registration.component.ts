import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private userService:UserService, private router: Router) { }

  ngOnInit(): void {
  }

  onClickSubmit(data:any) {
    
    this.userService.register({"username":data.username,"password":data.password}).subscribe({
      complete: () => {this.router.navigate(['/login'])},
      error: err => {
        alert(err.error)
      }
    });
    
 }

}
