import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  public notSamePasswords = false;

  public error:boolean = false;

  public errorMessage = "";

  constructor(private userService:UserService, private router: Router) { }

  ngOnInit(): void {
  }

  onClickSubmit(data:any) {

    if(data.password === data.repeatPassword)
      this.userService.register({"username":data.username,"email":data.email,"password":data.password}).subscribe({
        complete: () => {this.router.navigate(['/login'])},
        error: err => {
          this.error = true;
          this.errorMessage = err.error
        }
      });
    else
      this.notSamePasswords = true;
    
 }

}
