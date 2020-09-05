package freelance.paiement.donne.repositories;

import freelance.paiement.donne.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit,Long> {
}
