import {Component, Input,EventEmitter, OnInit, Output} from '@angular/core';
import {Moment} from 'moment';
import * as moment from 'moment';

@Component({
  selector: 'app-chart-header',
  templateUrl: './chart-header.component.html',
  styleUrls: ['./chart-header.component.css']
})
export class ChartHeaderComponent implements OnInit {

  @Output() periodChange = new EventEmitter<string>();
  @Output() dateChange = new EventEmitter<Moment>();

  @Input() periode: string = 'hebdomadaire';
  @Input() date: Moment = moment();

  periodes: string[] = ['hebdomadaire', 'mensuelle', 'annuelle'];

  constructor() { }

  ngOnInit(): void {
  }

  changePeriod(period: string): void {
    this.periode = period;
    this.periodChange.emit(period);
  }
  changeDate(date: Moment): void {
    console.log(date);
    this.date = date;
    this.dateChange.emit(date);
  }

}
