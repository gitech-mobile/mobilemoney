package freelance.paiement.donne.services.Impl;

import Enum.Canal;
import Enum.EtatPaiement;
import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.exceptions.ResourceNotFoundException;
import freelance.paiement.donne.models.Client;
import freelance.paiement.donne.models.Paiement;
import freelance.paiement.donne.models.Produit;
import freelance.paiement.donne.repositories.PaiementRepository;
import freelance.paiement.donne.repositories.PartnerRepository;
import freelance.paiement.donne.services.IClientService;
import freelance.paiement.donne.services.IPaiementService;
import freelance.paiement.donne.services.IProduitService;
import freelance.paiement.donne.specification.PaiementSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaiementService implements IPaiementService {

    private final PaiementRepository paiementRepository;
    private final IClientService clientService;
    private final PartnerRepository partnerRepository;
    private final IProduitService produitService;

    public PaiementService(PaiementRepository paiementRepository, IClientService clientService, PartnerRepository partnerRepository, IProduitService produitService) {
        this.paiementRepository = paiementRepository;
        this.clientService = clientService;
        this.partnerRepository = partnerRepository;
        this.produitService = produitService;
    }

    @Override
    @Transactional
    public Paiement save(Paiement paiement) throws CustomException {
        if(paiement.getId() != null)
            throw new CustomException("L'id doit etre null lors de la creation");

        if(paiement.getClient().getId() == null){
            try {
                paiement.setClient(clientService.findClient(paiement.getClient().getNumeroCompte()));
            } catch (Exception e) {
                paiement.setClient(clientService.saveClient(paiement.getClient()));
            }
        }

        if(paiement.getPartner().getId() == null)
            paiement.setPartner(partnerRepository.saveAndFlush(paiement.getPartner()));

        List<Produit> produitList = paiement.getProduits().stream()
                .filter(
                        produit -> produit.getId() == null
                )
                .map(
                        produit1 -> produitService.save(produit1)
                )
                .collect(Collectors.toList());

        paiement.setProduits(produitList);

        paiement.setDateupdate(Date.from(Instant.now()));
        return paiementRepository.save(paiement);
    }

    @Override
    public Page<Paiement> getAll(Pageable pageable) {
        return paiementRepository.findAll(pageable);
    }

    @Override
    public Page<Paiement> getPaiement(EtatPaiement etatPaiement, Canal canal, Pageable pageable) {
        return paiementRepository.findByEtatAndCanal(etatPaiement,canal, pageable);
    }

    @Override
    public Paiement findPaiement(EtatPaiement etatPaiement, Client client, Float montant) {
        return paiementRepository.findFirstByMontantAndClientAndEtat(montant,client,etatPaiement).orElseThrow(
                () -> new ResourceNotFoundException("Aucun paiement trouve pour le client "+ client.getNumeroCompte() + " montant " + montant + " etat " + etatPaiement )
        );
    }


    @Override
    public Paiement getById(Long id) {
        return paiementRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Aucun paiement trouve avec id = " +id)
        );
    }

    @Override
    public Paiement update(Paiement paiement) {
        paiementRepository.findById(paiement.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Aucun paiement avec l'id = " + paiement.getId()));
        paiement.setDateupdate(Date.from(Instant.now()));
        paiementRepository.save(paiement);
        return paiement;
    }

    @Override
    public Map<Month, Float> getStatYear(Long year, int etatPaiement) {
        TreeMap<Month, Float> map = new TreeMap<>();

        Arrays.stream(Month.values()).forEach(
                month -> {
                    map.put(month, paiementRepository.getSommeMois(year,month.getValue(),etatPaiement));
                });
        return map;
    }

    @Override
    public Map<LocalDate, Float> getStatMois(LocalDate date, int etatPaiement) {
        TreeMap<LocalDate, Float> map = new TreeMap<>();
        date.datesUntil(date.with(TemporalAdjusters.lastDayOfMonth())).forEach(
                localDate -> map.put(localDate,paiementRepository.getSommeJour(localDate.getDayOfMonth(),etatPaiement))
        );

        return map;
    }

    @Override
    public Map<DayOfWeek, Float> getStatWeek(Date date, int etatPaiement) {
        TreeMap<DayOfWeek, Float> map = new TreeMap<>();
        System.out.println(date);
        Date dateLoop;
        for (int i = 1;i <= 7;i++){
            dateLoop = Date.from(date.toInstant().minus(7-i, ChronoUnit.DAYS));
            System.out.println(dateLoop);
            map.put(
                    dateLoop.toInstant().atZone(ZoneId.systemDefault()).getDayOfWeek(),
                    paiementRepository.getSommeJour((LocalDate.from(dateLoop.toInstant().atZone(ZoneId.systemDefault()))).getDayOfMonth(),etatPaiement)
            );
            System.out.println(map);
        }
        return map;
    }

    @Override
    public Page<Paiement> search(EtatPaiement etatPaiement, Canal canal, String reference, Date dateInf, Date dateSup, Pageable pageable) {
        Specification<Paiement> specification = null;
        if (etatPaiement !=null)
            specification = PaiementSpecification.getPaiementByEtat(etatPaiement);
        if(canal !=null)
            specification = PaiementSpecification.addSpecification(specification,PaiementSpecification.getPaiementByCanal(canal));
        if(reference !=null)
            specification = PaiementSpecification.addSpecification(specification,PaiementSpecification.getPaiementByReference(reference));
        if(dateInf !=null)
            specification = PaiementSpecification.addSpecification(specification,PaiementSpecification.getPaiementByDateInf(dateInf));
         if(dateSup !=null)
            specification = PaiementSpecification.addSpecification(specification,PaiementSpecification.getPaiementByDateSup(dateSup));

         return paiementRepository.findAll(specification,pageable);
    }


}
