import {Component} from '@angular/core';
import {PaiementService} from '../../services/paiement.service';
import * as moment from 'moment';
import {EtatPaiement} from '../../services/Enum/EtatPaiement';
import {Moment} from 'moment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent  {
  public label: any;
  public stat: any;
  periode = 'hebdomadaire';
  date: Moment = moment();
  constructor(private paiementService: PaiementService) {
    this.label = [];
    this.stat = [];
    this.loadAll();
  }

  loadStat( line: number, etat: EtatPaiement): void{

    if (this.periode === 'annuelle') {
      this.paiementService.getStatYear(etat, this.date.year()).subscribe(
        stat => {
            this.label = Object.keys(stat.body);
            this.stat[line] =  Object.values(stat.body);
        }
      );
    }

    if (this.periode === 'mensuelle') {
      this.paiementService.getStatMonth(etat, this.date).subscribe(
        stat => {
            this.label = Object.keys(stat.body);
            this.stat[line] =  Object.values(stat.body);
        }
      );
    }

    if (this.periode === 'hebdomadaire') {
      this.paiementService.getStatWeek(etat, this.date).subscribe(
        stat => {
            this.label = Object.keys(stat.body);
            this.stat[line] =  Object.values(stat.body);
        }
      );
    }
  }

  loadAll(): void{
    this.loadStat(0, EtatPaiement.INITIER);
    this.loadStat(1, EtatPaiement.VALIDE);
    this.loadStat(2, EtatPaiement.ANNULER);
  }

  setPeriod($event: string): void{
    this.periode = $event;
    this.loadAll();
  }
  setDate($event: Moment): void{
    this.date = $event;
    if (this.periode !== 'annuelle') {
    this.loadAll();
    }
  }
}
