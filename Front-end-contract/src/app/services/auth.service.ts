import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { LoginUser } from '../models/login-user';
import { Observable } from 'rxjs';
import { JwtDto } from '../models/jwt-dto';

const BASE_URL = environment.BASE_URL;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}
  login(loginUser: LoginUser): Observable<JwtDto> {
    return this.http.post<JwtDto>(`${BASE_URL}/auth/login`, loginUser);
  }
}
