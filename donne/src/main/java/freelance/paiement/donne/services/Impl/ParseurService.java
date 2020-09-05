package freelance.paiement.donne.services.Impl;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Parseur;
import freelance.paiement.donne.models.Sms;
import freelance.paiement.donne.repositories.ParseurRepository;
import freelance.paiement.donne.services.IParseurService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ParseurService implements IParseurService {
    private final ParseurRepository parseurRepository;

    public ParseurService(ParseurRepository parseurRepository) {
        this.parseurRepository = parseurRepository;
    }

    @Override
    public Optional<Parseur> getParseur(Sms sms) {
        return parseurRepository.findAll().stream()
                .filter(
                parseur -> Pattern.compile(parseur.getRgxsms()).matcher(sms.getMessage()).find()
                )
                .findFirst()
                ;
    }

    @Override
    public Parseur save(Parseur parseur) throws CustomException {
        if(parseur.getId() != null)
            throw new CustomException("l'id du parseur doit etre null");
        return parseurRepository.save(parseur);
    }
}
