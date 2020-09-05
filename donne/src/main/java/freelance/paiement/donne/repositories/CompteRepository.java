package freelance.paiement.donne.repositories;

import freelance.paiement.donne.models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    Optional<Compte> findCompteById(Long id);
}
