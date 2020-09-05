package freelance.paiement.donne.controller;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Sms;
import Enum.EtatPaiement;

import freelance.paiement.donne.services.ITraitementService;
//import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/sms")
public class SmsController {
    private final Logger log = LoggerFactory.getLogger(SmsController.class);

    private final  ITraitementService traitementService;

    public SmsController(ITraitementService traitementService) {
        this.traitementService = traitementService;
    }


    @GetMapping
   // @Operation(summary = "sauvegarde des sms")
    public ResponseEntity<String> saveSms(
            String expediteur,
            String sim,
            String message,
            String time
    ) throws CustomException {
        log.debug("REST request saveSms : {}", message);

        Sms sms = Sms.builder()
                .expediteur(expediteur)
                .message(message)
                .time(time)
                .sim(sim)
                .date(new Date())
                .status(EtatPaiement.ECHEC)
                .build();

        traitementService.traitementSms(sms);
        return ResponseEntity.ok("traitement en cours");
    }
}
