import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private userService:UserService) { }

  ngOnInit(): void {
  }

  onClickSubmit(data:any) {

    this.userService.register({"username":data.username,"password":data.password})
    
 }

}
