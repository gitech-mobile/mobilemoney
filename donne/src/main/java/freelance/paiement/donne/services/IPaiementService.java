package freelance.paiement.donne.services;

import Enum.Canal;
import Enum.EtatPaiement;
import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Client;
import freelance.paiement.donne.models.Paiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.Map;

public interface IPaiementService {
    Paiement save(Paiement paiement) throws CustomException;
    Page<Paiement> getAll(Pageable pageable);
    Page<Paiement> getPaiement(EtatPaiement etatPaiement, Canal canal, Pageable pageable);
    Paiement findPaiement(EtatPaiement etatPaiement, Client client, Float montant);
    Paiement getById(Long id);
    Paiement update(Paiement paiement);
    Map<Month, Float> getStatYear(Long year, int etatPaiement);
    Map<LocalDate, Float> getStatMois(LocalDate date, int etatPaiement);
    Map<DayOfWeek, Float> getStatWeek(Date date, int etatPaiement);

    Page<Paiement> search(EtatPaiement etatPaiement,Canal canal,String reference,Date dateInf, Date dateSup, Pageable pageable);
}
