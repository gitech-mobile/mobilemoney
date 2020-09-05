package freelance.paiement.donne.models;

import Enum.EtatPartner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Partner {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String adresse;
    @Enumerated
    private EtatPartner etat;
    @JsonIgnore
    @OneToMany
    private List<Paiement> paiements;
    @OneToOne(cascade = CascadeType.ALL)
    private Photo logo;
    @OneToOne(cascade = CascadeType.ALL)
    private Photo contrat;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Compte compte;
}
