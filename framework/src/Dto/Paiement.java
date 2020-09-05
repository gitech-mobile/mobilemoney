package Dto;

import Enum.Canal;
import Enum.EtatPaiement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Paiement {
    private Long id;
    private Canal canal;
    private Date dateupdate;
    private EtatPaiement etat;
    private String reference;
    private String commentaire;
    private Float montant;
    private List<Produit> produits;
    private Client client;
    private Partner partner;
}
