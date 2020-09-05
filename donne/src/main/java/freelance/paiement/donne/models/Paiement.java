package freelance.paiement.donne.models;

import Enum.EtatPaiement;
import Enum.Canal;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Paiement {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated
    private Canal canal;
    private Date dateupdate;
    @Enumerated
    private EtatPaiement etat;
    private String reference;
    private String commentaire;
    @NotNull
    private Float montant;
    @ManyToMany 
    private List<Produit> produits;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Partner partner;
}
