import {
  HttpEvent, HttpHandler, HttpRequest, HttpErrorResponse,
  HttpInterceptor, HttpResponse
} from '@angular/common/http';
import { tap } from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlertService} from '../alert.service';
import {LoaderService} from '../loader.service';

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {
 constructor(private alertService: AlertService, private loaderService: LoaderService) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
   return next.handle(request).pipe(
      tap(
        (value: any) => {
          if (value instanceof HttpResponse) {
            this.loaderService.stopLoading();
          }
          if (value instanceof HttpRequest) {
            this.loaderService.startLoading();
          }
        },
        (err: any) => {
          if (err instanceof HttpErrorResponse) {
            this.loaderService.stopLoading();
            this.alertService.error( 'status: ' + err.status + ' message: ' + err.statusText );
          }
        }
      )
    );
  }
}
