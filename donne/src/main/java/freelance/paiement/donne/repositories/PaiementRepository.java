package freelance.paiement.donne.repositories;

import Enum.Canal;
import Enum.EtatPaiement;
import freelance.paiement.donne.models.Client;
import freelance.paiement.donne.models.Paiement;
import freelance.paiement.donne.models.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement,Long>, JpaSpecificationExecutor<Paiement> {
    Page<Paiement> findByEtatAndCanal(EtatPaiement etatpaiement, Canal canal, Pageable pageable);
    Optional<Paiement> findFirstByMontantAndClientAndEtat(Float montant, Client client, EtatPaiement etat);
    Page<Paiement> findAllByPartner(Partner partner, Pageable pageable);

    @Query(value = "SELECT SUM(montant) FROM paiement where EXTRACT (YEAR from dateupdate) = ?1 and EXTRACT (MONTH from dateupdate) = ?2 and etat = ?3"
            , nativeQuery = true)
    Float getSommeMois(Long year, Integer month, int etatpaiement);

    @Query(value = "SELECT SUM(montant) FROM paiement where EXTRACT (DAY from dateupdate) = ?1  and etat = ?2"
            , nativeQuery = true)
    Float getSommeJour(int day, int etatpaiement);

    @Query(value = "SELECT SUM(montant) FROM paiement where EXTRACT (YEAR from dateupdate) = ?1 and EXTRACT (MONTH from dateupdate) = ?2 and etat = ?3 and partner_id = ?4"
            , nativeQuery = true)
    Float getSommeMoisPartner(Long year, Integer month, int etatpaiement, Long partnerId);

    @Query(value = "SELECT SUM(montant) FROM paiement where EXTRACT (DAY from dateupdate) = ?1  and etat = ?2 and partner_id = ?3"
            , nativeQuery = true)
    Float getSommeJourPartner(int day, int etatpaiement, Long partnerId);
}
