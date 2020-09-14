import {Component, Input, EventEmitter, Output} from '@angular/core';
import {Moment} from 'moment';
import * as moment from 'moment';

@Component({
  selector: 'app-chart-header',
  templateUrl: './chart-header.component.html',
  styleUrls: ['./chart-header.component.css']
})
export class ChartHeaderComponent  {

  @Output() periodChange = new EventEmitter<string>();
  @Output() dateChange = new EventEmitter<Moment>();

  @Input() periode = 'hebdomadaire';
  @Input() date: Moment = moment();

  periodes: string[] = ['hebdomadaire', 'mensuelle', 'annuelle'];

  changePeriod(period: string): void {
    this.periode = period;
    this.periodChange.emit(period);
  }
  changeDate(date: Moment): void {
    this.date = date;
    this.dateChange.emit(date);
  }

}
