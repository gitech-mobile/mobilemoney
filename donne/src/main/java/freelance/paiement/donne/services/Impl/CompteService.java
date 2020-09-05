package freelance.paiement.donne.services.Impl;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.exceptions.CustomNotFoundException;
import freelance.paiement.donne.models.Compte;
import freelance.paiement.donne.repositories.CompteRepository;
import freelance.paiement.donne.services.ICompteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CompteService implements ICompteService {
    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }


    @Override
    @Transactional
    public Compte crediter(Long id, Float montant) throws CustomException {
        Compte compte;

        compte = compteRepository.findCompteById(id).orElseThrow(
                () ->  new CustomNotFoundException("compte invalide: "+id )
        );

        if(compte.getSolde() != null)
            compte.setSolde(compte.getSolde() + montant);
        else
            compte.setSolde(montant);
        compte.setDate(new Date());
        compteRepository.save(compte);
        return compte;
    }

    @Override
    @Transactional
    public Compte debiter(Long id, Float montant) {
        Compte compte;
        compte = compteRepository.findCompteById(id).orElseThrow(
                () ->  new CustomNotFoundException("compte invalide: "+id )
        );
        float solde =0;

        if(compte.getSolde() != null)
            solde = compte.getSolde() - montant;

        if ( solde < 0)
            throw new CustomNotFoundException("solde inferieur a  : "+montant );

        compte.setSolde(solde);

        compteRepository.save(compte);
        return compte;
    }

    @Override
    public Page<Compte> getAll(Pageable pageable) {
        return compteRepository.findAll(pageable);
    }

    @Override
    public Compte save(Compte compte) throws CustomException {
        if(compte.getId() != null)
            throw new CustomException("L'id doit etre null lors de la creation");

        return compteRepository.save(compte);
    }

}
