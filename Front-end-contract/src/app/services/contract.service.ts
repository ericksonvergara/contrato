import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TokenService } from './token.service';

const BASE_URL = environment.BASE_URL;

@Injectable({
  providedIn: 'root',
})
export class ContractService {
  constructor(private tokenService: TokenService, private http: HttpClient) {}

  uploadFiles(file: File[]) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.tokenService.getToken()}`,
    });

    let multipar: FormData = new FormData();
    for (let i = 0; i < file.length; i++) {
      multipar.append('contracts', file[i], file[i].name);
    }

    return this.http
      .post(`${BASE_URL}/api/contract/upload`, multipar, { headers })
      .pipe((resp: any) => {
        return resp;
      });
  }

  getContracts() {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.tokenService.getToken()}`,
    });
    return this.http
      .get(`${BASE_URL}/api/contract/admin_contracts`, { headers })
      .pipe((resp: any) => {
        return resp;
      });
  }

  accpteContract(dni: string, isAccept: boolean) {
    let headers = new HttpHeaders({
      Authorization: `Bearer ${this.tokenService.getToken()}`,
    });
    const resp = this.http.put(
      `${BASE_URL}/api/contract/accept?dni=${dni}&isAccept=${isAccept}`,
      {},
      { headers }
    );

    return resp;
  }
}
