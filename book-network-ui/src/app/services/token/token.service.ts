import { Injectable } from '@angular/core';
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  get name() {
    const token = this.token;
    if (!token) {
      return '';
    }
    const jwtHelper = new JwtHelperService();
    return jwtHelper.decodeToken(token).fullName.split(' ')[0];
  }

  get token() {
    return localStorage.getItem('token') as string;
  }

  set token(token: string) {
    localStorage.setItem('token', token);
  }

  isTokenNotValid() {
    return !this.isTokenValid();
  }

  private isTokenValid() {
    const token = this.token;
    if (!token) {
      return false;
    }
    const jwtHelper = new JwtHelperService();
    const isTokenExpired = jwtHelper.isTokenExpired(token);
    if (isTokenExpired) {
      localStorage.clear();
      return false;
    }
    return true;
  }
}
