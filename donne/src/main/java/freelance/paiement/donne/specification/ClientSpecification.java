package freelance.paiement.donne.specification;

import freelance.paiement.donne.models.Client;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ClientSpecification {
    public static Specification<Client> getClientNomLike(String nom){
        return new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("nom"), nom);
            }
        };
    }
    public static Specification<Client> getClientPrenomLike(String prenom){
        return new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("prenom"), prenom);
            }
        };
    }
    public static Specification<Client> getClientByNumeroCompte(String numeroCompte){
        return new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("numeroCompte"), numeroCompte);
            }
        };
    }
    public static Specification<Client> getClientByCin(String cin){
        return new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("cin"), cin);
            }
        };
    }

    public static Specification<Client> addSpecification(Specification<Client> s, Specification<Client> added){
        if(s !=null)
            return s.and(added);
        else
            return added;
    }
}
