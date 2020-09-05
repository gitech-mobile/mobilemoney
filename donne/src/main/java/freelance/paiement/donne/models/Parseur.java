package freelance.paiement.donne.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import Enum.Canal;

@Entity
@Getter
@Setter
public class Parseur {
    @Id
    @GeneratedValue
    private Long id;
    private Canal canal;
    private String senederid;
    private String rgxsms;
    private String rgxmontant;
    private String rgxtelephone;
    private String rgxreference;

}
