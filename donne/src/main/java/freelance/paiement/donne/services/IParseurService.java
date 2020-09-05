package freelance.paiement.donne.services;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Parseur;
import freelance.paiement.donne.models.Sms;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IParseurService {
    Optional<Parseur> getParseur(Sms sms);

    Parseur save(Parseur parseur) throws CustomException;
}
