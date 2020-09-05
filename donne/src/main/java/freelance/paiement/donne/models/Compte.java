package freelance.paiement.donne.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Compte {
    @Id
    @GeneratedValue
    private Long id;
    private Float solde;
    private String Devise;
    private Date date;
}
