package freelance.paiement.donne.services.Impl;

import freelance.paiement.donne.exceptions.ResourceNotFoundException;
import freelance.paiement.donne.models.Client;
import freelance.paiement.donne.repositories.ClientRepository;
import freelance.paiement.donne.services.IClientService;
import freelance.paiement.donne.specification.ClientSpecification;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class ClientService implements IClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client findClient(String numeroCompte) {
       return clientRepository.findFirstByNumeroCompteLike(numeroCompte).orElseThrow(
               () -> new ResourceNotFoundException("Aucun client avec le numero = " + numeroCompte)
       );
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.saveAndFlush(client);
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Page<Client> search( Client client , Pageable pageable) {
        Specification<Client> specification = null;

        if(client.getNom() !=null )
            specification =  ClientSpecification.getClientNomLike(client.getNom());

        if(client.getPrenom() !=null )
            specification = ClientSpecification.addSpecification(specification, ClientSpecification.getClientPrenomLike(client.getPrenom()));

        if(client.getNumeroCompte() !=null)
         specification = ClientSpecification.addSpecification(specification, ClientSpecification.getClientByNumeroCompte(client.getNumeroCompte()));

        if (client.getCin() !=null)
            specification = ClientSpecification.addSpecification(specification,ClientSpecification.getClientByCin(client.getCin()));

        return clientRepository.findAll(specification, pageable);
    }

    @Override
    public boolean deleteClient(Long id) {
        clientRepository.deleteById(id);
        return true;
    }

}
