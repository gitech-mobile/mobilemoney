package freelance.paiement.donne.controller;


import Enum.Canal;
import Enum.EtatPaiement;
import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Paiement;
import freelance.paiement.donne.services.IPaiementService;
import freelance.paiement.donne.utils.PaginationUtil;
//import io.swagger.annotations.Api;
//import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.util.annotation.Nullable;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/paiement")
public class PaiementController {
    private final IPaiementService paiementService;

    public PaiementController(IPaiementService paiementService) {
        this.paiementService = paiementService;
    }

    /**
     * {@code GET  /paiements} : Recupere tous les paiements.
     *
     * @param pageable la pagination des informations.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des paiements dans le body.
     */
   // @Operation(summary = "Récupère l'ensemble des paiements du système")
    @GetMapping
    public ResponseEntity<List<Paiement>> all(Pageable pageable) {
        Page<Paiement>  page= paiementService.getAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paiements/{canal}/{Etatpaiement}} : Recupere tous les paiements.
     *
     * @param page la pagination des informations.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des paiements dans le body.
     */
   // @Operation(summary = "Récupère les paiements par canal et etat")
    @GetMapping("/scheduler")
    public ResponseEntity<List<Paiement>> getpaiement(
            Canal canal,
            EtatPaiement etatPaiement,
            @PageableDefault(size = 1) Pageable page) {
        List<Paiement> paiements = paiementService.getPaiement(etatPaiement, canal, page).getContent();
        paiements.forEach(
                paiement -> {
                    paiement.setEtat(EtatPaiement.ENCOURS);
                    paiementService.update(paiement);
                }
        );
        paiements.stream().forEach(
                paiement -> {
                    paiement.getPartner().setLogo(null);
                    if(paiement.getPartner().getContrat() !=null)
                    paiement.getPartner().getContrat().setPhoto(null);
                }
        );
        return ResponseEntity.ok(paiements);
    }

    /**
     * {@code GET  /paiements/search : Recupere tous les paiements selon les spec.
     *
     * @param pageable la pagination des informations.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des paiements dans le body.
     */
    //@Operation(summary = "Recherche multicritere sur les paiements")
    @GetMapping("/search")
    public ResponseEntity<List<Paiement>> search(
            @Nullable String reference, @Nullable EtatPaiement etatPaiement, @Nullable Canal canal,
            @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateInf,
            @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateSup,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Paiement> page = paiementService.search(etatPaiement,canal,reference,dateInf,dateSup,pageable);

        page.stream().forEach(
                paiement -> {
                    paiement.getPartner().setLogo(null);

                    if(paiement.getPartner().getContrat() !=null)
                        paiement.getPartner().getContrat().setPhoto(null);
                }
        );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paiements/{canal}/{Etatpaiement}} : Recupere tous les paiements.
     *
     * @param date la date.
     * @param etatPaiement etatpaiement
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des paiements dans le body.
     */
    //@Operation(summary = "Récupère les paiements par canal et etat")
    @GetMapping("/statmonth")
    public ResponseEntity<Map<LocalDate, Float>> getStatMois(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, EtatPaiement etatPaiement) {
        return ResponseEntity.ok(paiementService.getStatMois(LocalDate.from(date.toInstant().atZone(ZoneId.systemDefault())), etatPaiement.ordinal()));
    }

    /**
     * {@code GET  /paiements/{canal}/{Etatpaiement}} : Recupere tous les paiements.
     *
     * @param year l'annee.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des paiements dans le body.
     */
    //@Operation(summary = "Récupère les paiements par canal et etat")
    @GetMapping("/statyear")
    public ResponseEntity<Map<Month, Float>> getStatYear( Long year, EtatPaiement etatPaiement) {
        return ResponseEntity.ok(paiementService.getStatYear(year, etatPaiement.ordinal()));
    }

    /**
     * {@code GET  /paiements/{canal}/{Etatpaiement}} : Recupere tous les paiements.
     *
     * @param date date.
     * @return the {@link ResponseEntity} avec status {@code 200 (OK)} et la liste des paiements dans le body.
     */
    //@Operation(summary = "Récupère les paiements par canal et etat")
    @GetMapping("/statweek")
    public ResponseEntity<Map<DayOfWeek, Float>> getStatWeek(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, EtatPaiement etatPaiement) {
        return ResponseEntity.ok(paiementService.getStatWeek(date,etatPaiement.ordinal()));
    }

    /**
     *
     * @param id du paiement à rechercher
     * @return ResponseEntity
     */
    //@Operation(summary = "Récupère les informations d'un paiement par son id")
    @GetMapping("/id")
    public ResponseEntity<Paiement> findPaiementById(Long id) {
        return ResponseEntity.ok(paiementService.getById(id));
    }
    /**
     *
     * @param paiement
     * @return ResponseEntity
     */
    //@Operation(summary = "Permet de faire un update sur un paiement")
    @PutMapping
    public ResponseEntity<Paiement> findServerById(
            @RequestBody @Valid
                    Paiement paiement) {
        return ResponseEntity.ok(paiementService.update(paiement));
    }

    /**
     *Initier un paiement
     * @param paiement à initier
     * @return ResponseEntity
     */
    //@Operation(summary = "Permet lancer le process du paiement")
    @PostMapping
    public ResponseEntity<Paiement> add(
            @RequestBody @Valid
            Paiement paiement
            ) throws CustomException {
        paiement.setEtat(EtatPaiement.ATTENTE);
        return ResponseEntity.ok(paiementService.save(paiement));
    }

}
