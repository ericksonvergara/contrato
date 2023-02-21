import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ContractService } from 'src/app/services/contract.service';
import { TokenService } from 'src/app/services/token.service';
import { addImgPerson } from 'src/app/util/addImg';

@Component({
  selector: 'app-dasboard',
  templateUrl: './dasboard.component.html',
  styleUrls: ['./dasboard.component.css'],
})
export class DasboardComponent implements OnInit {
  contracts: any = null;
  fileList: any[] = [];
  path: any = null;
  isContract: boolean = false;
  imgs: string[] = [];

  constructor(
    private tokenService: TokenService,
    private router: Router,
    private contractService: ContractService
  ) {}

  ngOnInit(): void {
    this.getContracts();
  }

  loginOut() {
    this.tokenService.logOut();
    this.router.navigateByUrl('login');
  }

  getContracts() {
    this.contractService.getContracts().subscribe((resp: any) => {
      console.log(resp);
      if (resp.ok) {
        let dataAccep: any[] = resp.data;
        dataAccep = dataAccep.filter(
          (contract: any) => contract.accept === false
        );
        this.contracts = addImgPerson(dataAccep);
        console.log(this.contracts);
      }
    });
  }

  showContractsAccept(value: string) {
    let isAccept: boolean = value === 'Pendientes' ? false : true;
    this.contractService.getContracts().subscribe((resp: any) => {
      if (resp.ok) {
        let dataAccep: any[] = resp.data;
        dataAccep = dataAccep.filter(
          (contract: any) => contract.accept === isAccept
        );
        this.contracts = addImgPerson(dataAccep);
        console.log(this.contracts);
      }

      this.isContract = false;
    });
  }

  fileChange(event: any) {
    let fileList: FileList = event.target.files;
    console.log(fileList);
    if (fileList.length > 0) {
      for (let i = 0; i < fileList.length; i++) {
        this.fileList.push(fileList[i]);
      }
    }
  }

  uploadFiles() {
    this.contractService.uploadFiles(this.fileList).subscribe((data: any) => {
      console.log(this.fileList, 'list de archivos');
      this.fileList = [];
      this.getContracts();
    });
  }
  cancel() {
    this.fileList = [];
  }

  showImg(contract: any) {
    localStorage.removeItem('contract');
    localStorage.setItem('contract', JSON.stringify(contract));
  }
}
