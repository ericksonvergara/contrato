import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { DasboardComponent } from './pages/dasboard/dasboard.component';
import { ContractComponent } from './pages/contract/contract.component';
import { TokenImgPipe } from './pipes/token-img.pipe';
import { ImageComponent } from './pages/image/image.component';
import { FileContractComponent } from './pages/file-contract/file-contract.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NotFoundComponent,
    DasboardComponent,
    ContractComponent,
    TokenImgPipe,
    ImageComponent,
    FileContractComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
