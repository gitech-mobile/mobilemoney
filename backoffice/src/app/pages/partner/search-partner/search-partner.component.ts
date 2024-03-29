  import {Component, EventEmitter, Output} from '@angular/core';
  import {EtatPartner} from '../../../services/Enum/EtatPartner';

  @Component({
  selector: 'app-search-partner',
  templateUrl: './search-partner.component.html'
})
export class SearchPartnerComponent {
  classToggled = true;
  @Output() partnerChange = new EventEmitter();
  EtatPartnerList = Object.keys(EtatPartner);
  etatPartner: EtatPartner;
  nom: string;
  toggleField(): void {
    this.classToggled = !this.classToggled;
    this.nom          = null;
    this.etatPartner  = null;
    this.search();
  }

   search(): void {
    this.partnerChange.emit({
      nom: this.nom,
      etatPartner: this.etatPartner
    });
  }
}
