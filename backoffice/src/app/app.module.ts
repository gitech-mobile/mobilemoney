import { BrowserModule } from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import {PagesModule} from './pages/pages.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NbEvaIconsModule} from '@nebular/eva-icons';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {NbDatepickerModule} from '@nebular/theme';
import {KeycloakAngularModule, KeycloakService} from 'keycloak-angular';
import {initializer} from './services/initKeycloak';
import {ErrorHandlerInterceptor} from './services/interceptor/ErrorHandlerInterceptor.interceptor';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    NbEvaIconsModule,
    PagesModule,
    NgbModule,
    KeycloakAngularModule,
    NbDatepickerModule.forRoot(),
  ]
  ,
  providers: [
    { provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true,
      deps: [KeycloakService]
    }
  ],
    bootstrap: [AppComponent]
})
export class AppModule { }
