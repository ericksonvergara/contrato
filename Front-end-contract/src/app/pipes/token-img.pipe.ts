import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Pipe, PipeTransform } from '@angular/core';
import { from, Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { TokenService } from '../services/token.service';

@Pipe({
  name: 'tokenImg',
})
export class TokenImgPipe implements PipeTransform {
  constructor(private tokenService: TokenService, private http: HttpClient) {}

  async transform(url: string): Promise<string> {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + this.tokenService.getToken(),
    });
    const imageBlob =
      (await this.http
        .get(url, { headers, responseType: 'blob' })
        .toPromise()) || null;
    const reader = new FileReader();

    return new Promise((resolve, reject) => {
      reader.onloadend = () => resolve(reader.result as string);
      if (imageBlob) {
        reader.readAsDataURL(imageBlob);
      }
    });
  }
}
