package freelance.paiement.donne.specification;

import Enum.EtatPaiement;
import Enum.Canal;

import freelance.paiement.donne.models.Paiement;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;


public class PaiementSpecification {
    public static Specification<Paiement> getPaiementByEtat(EtatPaiement etatPaiement){
        return new Specification<Paiement>() {
            @Override
            public Predicate toPredicate(Root<Paiement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("etat"), etatPaiement.ordinal());
            }
        };
    }
    public static Specification<Paiement> getPaiementByCanal(Canal canal){
        return new Specification<Paiement>() {
            @Override
            public Predicate toPredicate(Root<Paiement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("canal"), canal.ordinal());
            }
        };
    }
    public static Specification<Paiement> getPaiementByReference(String reference){
        return new Specification<Paiement>() {
            @Override
            public Predicate toPredicate(Root<Paiement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("reference"), reference);
            }
        };
    }
    public static Specification<Paiement> getPaiementByPartnerId(Long partner_id){
        return new Specification<Paiement>() {
            @Override
            public Predicate toPredicate(Root<Paiement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("partner_id"), partner_id);
            }
        };
    }
    public static Specification<Paiement> getPaiementByDateInf(Date date){
        return new Specification<Paiement>() {
            @Override
            public Predicate toPredicate(Root<Paiement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThan(root.get("dateupdate"), date);
            }
        };
    }
    public static Specification<Paiement> getPaiementByDateSup(Date date){
        return new Specification<Paiement>() {
            @Override
            public Predicate toPredicate(Root<Paiement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThan(root.get("dateupdate"), date);
            }
        };
    }

    public static Specification<Paiement> addSpecification(Specification<Paiement> s, Specification<Paiement> added){
        if(s !=null)
            return s.and(added);
        else
            return added;
    }
}
