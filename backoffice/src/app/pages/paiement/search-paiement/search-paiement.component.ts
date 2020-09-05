import {Component,EventEmitter, Output} from '@angular/core';
import {EtatPaiement} from '../../../services/Enum/EtatPaiement';
import {Canal} from '../../../services/Enum/Canal';
import {Moment} from 'moment';

@Component({
  selector: 'app-search-paiement',
  templateUrl: './search-paiement.component.html'
})
export class SearchPaiementComponent {
  classToggled: boolean = true;
  @Output() paiementChange = new EventEmitter();
  EtatPaiementList = Object.keys(EtatPaiement);
  CanalList        = Object.keys(Canal);
  dateInf: Moment;
  reference: string;
  dateSup: Moment;
  canal: Canal;
  etat: EtatPaiement;

  toggleField() {
    this.classToggled = !this.classToggled;
    this.dateInf      = null;
    this.reference    = null;
    this.dateInf      = null;
    this.canal        = null;
    this.etat         = null;
    this.search();
  }

  search() {
    this.paiementChange.emit({
      reference: this.reference,
      dateInf: this.dateInf?this.dateInf.format('YYYY-MM-DD'):null,
      dateSup: this.dateSup?this.dateSup.format('YYYY-MM-DD'):null,
      canal: this.canal,
      etat: this.etat
    });

  }
}
