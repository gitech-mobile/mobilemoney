package freelance.paiement.donne.services.Impl;

import freelance.paiement.donne.models.Partner;
import freelance.paiement.donne.repositories.PartnerRepository;
import freelance.paiement.donne.services.IPartnerService;
import Enum.EtatPartner;

import freelance.paiement.donne.specification.PartnerSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PartnerService implements IPartnerService {
    private final PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public Partner save(Partner partner) {
        return partnerRepository.save(partner);
    }

    @Override
    public Page<Partner> getAll(Pageable pageable) {
        return partnerRepository.findAll(pageable);
    }

    @Override
    public Partner update(Partner partner) {
        return partnerRepository.save(partner);
    }

    @Override
    public Page<Partner> search(String nom, EtatPartner etatPartner, Pageable pageable) {
        Specification<Partner> specification = null;
        if(nom !=null )
            specification = PartnerSpecification.getPartnerNomLike(nom);
        if(etatPartner !=null)
            specification = PartnerSpecification.addSpecification(specification, PartnerSpecification.getPartnerEtat(etatPartner));

        return partnerRepository.findAll(specification,pageable);
    }


    @Override
    public Boolean deleteClient(Long id) {
        partnerRepository.deleteById(id);
        return true;
    }
}
