package freelance.paiement.donne.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    private Long id;
    private String adresse;
    @Unique
    private String numeroCompte;
    private String departement;
    private String cin;
    private Date finCin;
    private String nom;
    private String pays;
    private String prenom;
    private String region;
    @JsonIgnore
    @OneToMany
    private List<Paiement> paiements;
}
