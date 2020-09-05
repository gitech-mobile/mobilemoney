package freelance.paiement.donne.services;

import Enum.EtatPartner;
import freelance.paiement.donne.models.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPartnerService {
    Partner save(Partner partner) ;
    Page<Partner> getAll(Pageable pageable);
    Partner update(Partner partner);
    Page<Partner> search(String nom, EtatPartner etatPartner, Pageable pageable);
    Boolean deleteClient(Long id);
}
