import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  open: boolean = false;
  isLogged: boolean = false;

  constructor(private userService: UserService,  private router: Router) { 
    this.isLogged = userService.isLogged();
  }

  ngOnInit(): void {
    
  }

  showSearch(event:any){
    this.open = true;
  }

  closeSearch()
  {
    this.open = false;
  }

  logout()
  {
    this.userService.logout();
    window.location.reload();
  }

  searchProperties(data:any){
    window.location.href = `/properties/?search=${data.search}`
  }

}
