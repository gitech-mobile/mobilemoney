package freelance.paiement.donne.services.Impl;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.exceptions.ResourceNotFoundException;
import freelance.paiement.donne.models.Client;
import freelance.paiement.donne.models.Paiement;
import freelance.paiement.donne.models.Parseur;
import freelance.paiement.donne.models.Sms;
import freelance.paiement.donne.services.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Enum.EtatPaiement.INITIER;
import static Enum.EtatPaiement.VALIDE;

@Service
public class TraitementService implements ITraitementService {
    private final IPaiementService paiementService;
    private final ISmsService smsService;
    private final IClientService clientService;
    private final IParseurService parseurService;
    private final ICompteService compteService;

    public TraitementService(IPaiementService paiementService, ISmsService smsService, IClientService clientService, IParseurService parseurService, ICompteService compteService) {
        this.paiementService = paiementService;
        this.smsService = smsService;
        this.clientService = clientService;
        this.parseurService = parseurService;
        this.compteService = compteService;
    }

    public String parser(Pattern pattern, Sms sms){
        Matcher matcher = pattern.matcher(sms.getMessage());
        if(matcher.find())
        return matcher.group(2).replace(" ","");
        else
            return null;
    }

    @Override
    @Async
    public void traitementSms(Sms sms) throws CustomException {
        Parseur parseur = parseurService.getParseur(sms).orElseThrow(
                () -> new ResourceNotFoundException("Aucun parseur avec le message =  " + sms.getMessage()));
        sms.setCanal(parseur.getCanal());
        smsService.saveSms(sms);
        String numeroCompte = parser(Pattern.compile(parseur.getRgxtelephone()),sms);
        String montant = parser(Pattern.compile(parseur.getRgxmontant()),sms);
        String reference = parser(Pattern.compile(parseur.getRgxreference()),sms);
        Client client     = clientService.findClient(numeroCompte);
        Paiement paiement = paiementService.findPaiement(INITIER,client,Float.valueOf(montant));
        compteService.crediter(paiement.getPartner().getCompte().getId(), paiement.getMontant());
        sms.setStatus(VALIDE);
        paiement.setEtat(VALIDE);
        paiement.setReference(reference);
        paiementService.update(paiement);
        smsService.saveSms(sms);
    }

}
