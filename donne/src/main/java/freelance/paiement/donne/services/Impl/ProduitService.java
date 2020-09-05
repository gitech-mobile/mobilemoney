package freelance.paiement.donne.services.Impl;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.exceptions.ResourceNotFoundException;
import freelance.paiement.donne.models.Produit;
import freelance.paiement.donne.repositories.ProduitRepository;
import freelance.paiement.donne.services.IProduitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProduitService implements IProduitService {
    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public Produit save(Produit produit) {
       /* if(produit.getId() !=null)
            throw new CustomException("L'id du produit etre null lors de la creation");
*/        return produitRepository.save(produit);
    }

    @Override
    public Page<Produit> getAll(Pageable pageable) {
        return produitRepository.findAll(pageable);
    }

    @Override
    public Produit getById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("aucun produit avec l'id = "+id));
    }

    @Override
    public Produit update(Produit produit) {
        return produitRepository.findById(produit.getId())
                .map(produit1 -> produitRepository.save(produit))
                .orElseThrow(() -> new ResourceNotFoundException("Aucun produit avec l'id = "+produit.getId()));
    }
}
