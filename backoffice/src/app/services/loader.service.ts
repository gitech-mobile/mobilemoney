import { Injectable } from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoaderService {
  private loading  = false;
  loadingStatus     = new Subject();

  startLoading(): void {
    this.loading = true;
    this.loadingStatus.next( true);
  }

  stopLoading(): void {
    this.loading = false;
    this.loadingStatus.next(false);
  }
}
