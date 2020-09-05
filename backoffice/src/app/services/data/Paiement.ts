import {Canal} from '../Enum/Canal';
import {Moment} from 'moment';
import {EtatPaiement} from '../Enum/EtatPaiement';
import {Produit} from './Produit';
import {Partner} from './Partner';
import {Client} from './Client';

export class Paiement {
  id: number;
  reference: string;
  commentaire: string;
  canal: Canal;
  dateupdate: Moment;
  etat: EtatPaiement;
  montant: number;
  produits: Array<Produit>;
  client: Client ;
  partner: Partner;
}
