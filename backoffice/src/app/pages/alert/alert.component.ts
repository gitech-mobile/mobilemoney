import {Component, HostBinding, OnDestroy, OnInit} from '@angular/core';
import {NbToastrService} from '@nebular/theme';
import {AlertService} from '../../services/alert.service';
import {Alert} from '../../services/data/Alert';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent implements OnInit, OnDestroy {
  private subscription: Subscription;


  show = false;
  constructor(private toastrService: NbToastrService, private alertService: AlertService) { }

  @HostBinding('class')
  classes = 'alerte';

  ngOnInit(): void {
    console.log('alertComponent');
    this.subscription = this.alertService.getAlert()
      .subscribe(
      value => {
        this.show = true;
        this.showToast(value);
      }
    );
  }
  showToast(alert: Alert): void{
    if (alert) {
    this.toastrService.show(alert.status, alert.message, { status: alert.status, destroyByClick: true, duration: 5000});
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
