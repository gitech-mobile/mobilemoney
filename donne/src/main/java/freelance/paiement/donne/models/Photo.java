package freelance.paiement.donne.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Getter
@Setter
public class Photo {
    @Id
    @GeneratedValue
    private Long id;
    @Lob
    private byte[] photo;
    private String contentType;
}
