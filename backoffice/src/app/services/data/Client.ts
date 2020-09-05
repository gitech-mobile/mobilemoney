import {Moment} from 'moment';
import {Paiement} from './Paiement';

export class Client {
  id: number;
  adresse: string;
  numeroCompte: string;
  departement: string;
  cin: string;
  finCin: Moment;
  nom: string;
  pays: string;
  prenom: string;
  region: string;
  //paiements: Array<Paiement>;
}
