package freelance.paiement.donne.controller;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Parseur;
import freelance.paiement.donne.services.IParseurService;
//import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/parseur")
public class ParseurController {
    private final IParseurService parseurService;

    public ParseurController(IParseurService parseurService) {
        this.parseurService = parseurService;
    }
    @PostMapping
    //@Operation(summary = "ajout parseur")
    public ResponseEntity<Parseur> save(
            @RequestBody Parseur parseur
    ) throws CustomException {
        return ResponseEntity.ok(parseurService.save(parseur));
    }
}
