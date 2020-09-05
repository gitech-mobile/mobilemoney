package freelance.paiement.donne.repositories;

import freelance.paiement.donne.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ClientRepository extends JpaRepository< Client, Long>, JpaSpecificationExecutor<Client> {
    Optional<Client> findFirstByNumeroCompteLike(String numeroCompte);
}
