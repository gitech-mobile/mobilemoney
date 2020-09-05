package freelance.paiement.donne.models;

import lombok.*;

import Enum.EtatPaiement;
import Enum.Canal;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sms {
    @Id
    @GeneratedValue
    private Long id;
    private String expediteur;
    private Date date;
    private String sim;
    private String time;
    private String message;
    @Enumerated
    private EtatPaiement status;
    private Canal canal ;
}
