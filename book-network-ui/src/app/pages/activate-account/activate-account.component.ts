import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { AuthenticationService } from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent implements OnInit {

  message: string = '';
  isOkay: boolean = true;
  submitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    const token = this.activatedRoute.snapshot.queryParams['token'];
    if (token) {
      this.confirmAccount(token);
    }
  }

  onCodeCompleted(token: string) {
    this.confirmAccount(token);
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }

  private confirmAccount(token: string) {
    this.authService.confirm({
      token
    }).subscribe({
      next: () => {
        this.message = 'Your account has been successfully activated.\nNow you can proceed to login';
        this.submitted = true;
        this.isOkay = true;
      },
      error: () => {
        this.message = 'Token has been expired or invalid';
        this.submitted = true;
        this.isOkay = false;
      }
    })
  }
}
