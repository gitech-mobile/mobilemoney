package freelance.paiement.donne.models;

import Enum.EtatProduit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Produit{
    @Id
    @GeneratedValue
    private Long id;
    private String details;
    @Enumerated
    private EtatProduit etat;
    private String identifiant;
    @NotNull
    private Float montant;
    private String nom;
    private Integer quantite;
}
