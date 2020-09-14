package freelance.paiement.donne.controller;

import freelance.paiement.donne.models.Client;
import freelance.paiement.donne.services.IClientService;
import freelance.paiement.donne.utils.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.util.annotation.Nullable;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientController {
    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    /***
     *
     * @param pageable
     * @return ResponseEntity<List<Client>>
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_PARTNER','ROLE_SUPPORT')")
    ResponseEntity<List<Client>> all(Pageable pageable){
        Page<Client> page = clientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /***
     *
     * @param client
     * @return ResponseEntity<Client>
     */
    @PutMapping
    @PreAuthorize ("hasAnyRole('ROLE_SUPPORT')")
    ResponseEntity<Client> put(
            @RequestBody @Valid
            Client client
    ){
        return ResponseEntity.ok(clientService.updateClient(client));
    }
    /***
     *
     * @return ResponseEntity<Client>
     */
    @GetMapping(value = "/search")
    @PreAuthorize ("hasAnyRole('ROLE_PARTNER','ROLE_SUPPORT')")
    ResponseEntity<List<Client>> search(
            @Nullable String nom, @Nullable String prenom, @Nullable String numeroCompte, @Nullable String cin,
            @PageableDefault(size = 10) Pageable pageable
    ){
        Client client = Client.builder()
                .nom(nom)
                .prenom(prenom)
                .numeroCompte(numeroCompte)
                .cin(cin)
                .build();
        Page<Client> page = clientService.search(client, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /***
     *
     * @param client
     * @return ResponseEntity<Client>
     */
    @PostMapping
    @PreAuthorize ("hasAnyRole('ROLE_SUPPORT')")
    ResponseEntity<Client> post(
            @RequestBody @Valid
            Client client
    ){
        return ResponseEntity.ok(clientService.saveClient(client));
    }

    /***
     *
     * @param id
     * @return ResponseEntity<Client>
     */
    @DeleteMapping
    @PreAuthorize ("hasAnyRole('ROLE_ADMIN')")
    ResponseEntity<Boolean> delete(@RequestParam Long id){
        return ResponseEntity.ok(clientService.deleteClient(id));
    }
}
