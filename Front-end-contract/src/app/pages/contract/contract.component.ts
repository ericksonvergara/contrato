import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ContractService } from 'src/app/services/contract.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-contract',
  templateUrl: './contract.component.html',
  styleUrls: ['./contract.component.css'],
})
export class ContractComponent implements OnInit {
  img: string = '';
  img2: string = '';
  img3: string = '';
  contract: any = null;
  constructor(
    private tokenService: TokenService,
    private router: Router,
    private contractService: ContractService
  ) {}

  ngOnInit(): void {
    this.getContract();
  }

  getContract() {
    this.contract = JSON.parse(localStorage.getItem('contract') || '');
    console.log(this.contract.dni);
    // this.img = `http://172.16.10.171:8080/api/contract/showImgFront?dni=${this.contract.dni}`;
    // this.img2 = `http://172.16.10.171:8080/api/contract/showImgLater?dni=${this.contract.dni}`;
    // this.img3 = `http://172.16.10.171:8080/api/contract/showPerson?dni=${this.contract.dni}`;
    this.img = `http://localhost:8080/api/contract/showImgFront?dni=${this.contract.dni}`;
    this.img2 = `http://localhost:8080/api/contract/showImgLater?dni=${this.contract.dni}`;
    this.img3 = `http://localhost:8080/api/contract/showPerson?dni=${this.contract.dni}`;
  }

  loginOut() {
    this.tokenService.logOut();
    this.router.navigateByUrl('login');
  }

  checkContract(isAccept: boolean) {
    this.contract.dni;
    this.contractService
      .accpteContract(this.contract.dni, isAccept)
      .subscribe((resp) => {
        console.log(resp);
      });
  }
}
