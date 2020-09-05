package freelance.paiement.donne.repositories;

import freelance.paiement.donne.models.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartnerRepository extends JpaRepository<Partner, Long> , JpaSpecificationExecutor<Partner> {

}
