import {Partner} from './Partner';
import {Moment} from 'moment';

export class Compte {
  id: number;
  solde: number;
  numeroCompte: number;
  devise: string;
  partner: Partner;
  date: Moment;
}
