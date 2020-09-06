package freelance.paiement.donne.repositories;

import Enum.Canal;
import Enum.EtatPaiement;
import freelance.paiement.donne.models.Client;
import freelance.paiement.donne.models.Paiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement,Long>, JpaSpecificationExecutor<Paiement> {
    Page<Paiement> findByEtatAndCanal(EtatPaiement etatpaiement, Canal canal, Pageable pageable);
    Optional<Paiement> findFirstByMontantAndClientAndEtat(Float montant, Client client, EtatPaiement etat);

    @Query(value = "SELECT SUM(montant) FROM paiement where YEAR (dateupdate) = ?1 and MONTH(dateupdate) = ?2 and etat = ?3"
            , nativeQuery = true)
    Float getSommeMois(Long year, Integer month, int etatpaiement);

    @Query(value = "SELECT SUM(montant) FROM paiement where DAY (dateupdate) = ?1  and etat = ?2"
            , nativeQuery = true)
    Float getSommeJour(int day, int etatpaiement);
}
