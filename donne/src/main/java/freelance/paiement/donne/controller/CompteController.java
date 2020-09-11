package freelance.paiement.donne.controller;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Compte;
import freelance.paiement.donne.services.ICompteService;
import freelance.paiement.donne.utils.PaginationUtil;
//import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/compte")
public class CompteController {
    private final ICompteService compteService;

    public CompteController(ICompteService compteService) {
        this.compteService = compteService;
    }

    /**
     * {@code GET  /compte} : Recupere tous les compte.
     *
     * @param pageable la pagination des informations.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des comptes dans le body.
     */
    //@Operation(summary = "Récupère l'ensemble des comptes du système")
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_PARTNER','ROLE_ADMIN')")
    public ResponseEntity<List<Compte>> all(Pageable pageable) {
        Page<Compte> page= compteService.getAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     *Initier un paiement
     * @param compte à initier
     * @return ResponseEntity
     */
   // @Operation(summary = "Permet d ajouter un compte")
    @PostMapping
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Compte> add(
            @RequestBody @Valid
                    Compte compte
    ) throws CustomException {
        compte.setSolde((float) 0);
        return ResponseEntity.ok(compteService.save(compte));
    }
}
