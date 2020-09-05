import {Component, OnInit} from '@angular/core';
import {PaiementService} from '../../services/paiement.service';
import * as moment from 'moment';
import {EtatPaiement} from '../../services/Enum/EtatPaiement';
import {Moment} from 'moment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  public label: any;
  public stat: any;
  periode: string = 'hebdomadaire';
  date: Moment = moment();
  constructor(private paiementService: PaiementService) {
    this.label = [];
    this.stat = [];
  }

  ngOnInit(): void {
    this.loadAll();
  }

  loadStat( line: number, etat: EtatPaiement){
    console.log('loadstat');

    if(this.periode == 'annuelle')
      this.paiementService.getStatYear(etat, this.date.year()).subscribe(
        stat => {
            this.label = Object.keys(stat.body);

          console.log('finish loaded ' + line);
          this.stat[line] =  Object.values(stat.body);
        }
        ,
        error => {
          console.log(error);
        }
      );

    if(this.periode == 'mensuelle')
      this.paiementService.getStatMonth(etat,this.date).subscribe(
        stat => {
            this.label = Object.keys(stat.body);

          console.log('finish loaded month ' + line);
          this.stat[line] =  Object.values(stat.body);
        },

        error => {
          console.log(error);
        }
      );

    if(this.periode == 'hebdomadaire')
      this.paiementService.getStatWeek(etat,this.date).subscribe(
        stat => {
            this.label = Object.keys(stat.body);

          console.log('finish load week ' + line);
          this.stat[line] =  Object.values(stat.body);
        },

        error => {
          console.log(error);
        }
      );
  }

  loadAll(){
    this.loadStat(0,EtatPaiement.INITIER);
    this.loadStat(1,EtatPaiement.VALIDE);
    this.loadStat(2,EtatPaiement.ANNULER);
  }

  setPeriod($event: string) {
    this.periode = $event;
    this.loadAll();
  }
  setDate($event: Moment) {
    this.date = $event;
    if(this.periode != 'annuelle')
    this.loadAll();
  }
}
