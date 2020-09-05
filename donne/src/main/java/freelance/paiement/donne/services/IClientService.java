package freelance.paiement.donne.services;

import freelance.paiement.donne.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IClientService {
    Client findClient(String numeroCompte);
    Client saveClient(Client client);
    Client updateClient(Client client);
    Page<Client> findAll(Pageable pageable);
    Page<Client> search(Client client, Pageable pageable);

    boolean deleteClient(Long id);
}
