package freelance.paiement.donne.services;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICompteService {
    Compte crediter(Long id,Float montant) throws CustomException;
    Compte debiter(Long id,Float montant);
    /*Compte getCompte(Partner partner);
    Compte getSolde(Long numeroCompte);*/

    Page<Compte> getAll(Pageable pageable);

    Compte save(Compte compte) throws CustomException;
}
