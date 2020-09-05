package freelance.paiement.donne.services;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProduitService{
    Produit save(Produit produit) ;
    Page<Produit> getAll(Pageable pageable);
    Produit getById(Long id);
    Produit update(Produit produit);
}
