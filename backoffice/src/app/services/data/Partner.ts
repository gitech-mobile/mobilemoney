import {EtatPartner} from '../Enum/EtatPartner';
import {Paiement} from './Paiement';
import {Photo} from './Photo';
import {Compte} from './Compte';

export class Partner {
  id: number;
  nom: string;
  identifiant: string;
  email: string;
  adresse: string;
  logo: Photo;
  etat: EtatPartner;
  paiements: Array<Paiement>;
  compte: Compte;
  contrat: Photo;
}
