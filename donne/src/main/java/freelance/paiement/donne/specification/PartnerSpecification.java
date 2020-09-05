package freelance.paiement.donne.specification;

import freelance.paiement.donne.models.Partner;
import org.springframework.data.jpa.domain.Specification;
import Enum.EtatPartner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PartnerSpecification {
    public static Specification<Partner> getPartnerNomLike(String nom){
        return new Specification<Partner>() {
            @Override
            public Predicate toPredicate(Root<Partner> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("nom"), nom);
            }
        };
    }
    public static Specification<Partner> getPartnerEtat(EtatPartner etatPartner){
        return new Specification<Partner>() {
            @Override
            public Predicate toPredicate(Root<Partner> root, CriteriaQuery<?> query5 , CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("etat"), etatPartner.ordinal());
            }
        };
    }
    public static Specification<Partner> addSpecification(Specification<Partner> s, Specification<Partner> added){
        if(s !=null)
            return s.and(added);
        else
            return added;
    }
}
