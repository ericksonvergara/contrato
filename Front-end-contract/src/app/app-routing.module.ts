import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { ContractComponent } from './pages/contract/contract.component';
import { DasboardComponent } from './pages/dasboard/dasboard.component';
import { FileContractComponent } from './pages/file-contract/file-contract.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: LoginComponent },
  { path: 'dasboard', component: DasboardComponent },
  { path: 'contract', component: ContractComponent },
  { path: 'file', component: FileContractComponent },
  { path: '**', component: NotFoundComponent },
  { path: 'notFound', component: NotFoundComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
