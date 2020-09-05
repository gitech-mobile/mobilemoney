package freelance.paiement.donne.controller;

import Enum.EtatPartner;
import freelance.paiement.donne.models.Partner;
import freelance.paiement.donne.services.IPartnerService;
import freelance.paiement.donne.utils.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.util.annotation.Nullable;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/partner")
public class PartnerController {
    private final IPartnerService partnerService;

    public PartnerController(IPartnerService partnerService) {
        this.partnerService = partnerService;
    }

    /***
     *
     * @param pageable
     * @return ResponseEntity<List<Partner>>
     */
    @GetMapping
    ResponseEntity<List<Partner>> all(Pageable pageable){
        Page<Partner> page = partnerService.getAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /***
     *
     * @return ResponseEntity<Client>
     */
    @GetMapping(value = "/search")
    ResponseEntity<List<Partner>> search(
            @Nullable String nom, @Nullable EtatPartner etatPartner,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<Partner> page = partnerService.search(nom,etatPartner,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

        /***
         *
         * @param partner
         * @return ResponseEntity<Partner>
         */
    @PutMapping
    ResponseEntity<Partner> put(
            @RequestBody @Valid
            Partner partner){
        return ResponseEntity.ok(partnerService.save(partner));
    }
    /***
     *
     * @param partner
     * @return ResponseEntity<Partner>
     */
    @PostMapping
    ResponseEntity<Partner> post(
            @RequestBody @Valid
            Partner partner){
        return ResponseEntity.ok(partnerService.save(partner));
    }

    @DeleteMapping
    ResponseEntity<Boolean> delete(Long id){
        return ResponseEntity.ok(partnerService.deleteClient(id));
    }
}
