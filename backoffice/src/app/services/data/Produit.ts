import {EtatProduit} from '../Enum/EtatProduit';

export class Produit{
  id: number;
  details: string;
  etat: EtatProduit;
  identifiant: string;
  montant: number;
  nom: string;
  quantite: number;
}
