package freelance.paiement.donne.services.Impl;

import Enum.EtatPartner;
import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Partner;
import freelance.paiement.donne.repositories.PartnerRepository;
import freelance.paiement.donne.services.IPartnerService;
import freelance.paiement.donne.specification.PartnerSpecification;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.List;

import static Enum.RequiredAction.UPDATE_PASSWORD;
import static Enum.UserGroupe.gitech_partner;

@Service
public class PartnerService implements IPartnerService {
    private final PartnerRepository partnerRepository;

    @Value(value = "${keycloak.serverUrl}")
    private  String serverUrl;
    @Value(value = "${keycloak.clientId}")
    private  String clientId;
    @Value(value = "${keycloak.secret}")
    private  String secret;
    @Value(value = "${keycloak.realm}")
    private  String keycloakRealm;



    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    @Transactional
    public Partner save(Partner partner) throws CustomException {
        partner = partnerRepository.save(partner);
        addUser(partner);
        return partner;
    }

    private void addUser(Partner partner) throws CustomException {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setEmail(partner.getEmail());
        user.setFirstName(partner.getNom());
        user.setUsername(partner.getIdentifiant());

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(partner.getIdentifiant());
        user.setCredentials(List.of(credentialRepresentation));
        user.setGroups(List.of(gitech_partner.toString()));
        user.setRequiredActions(List.of(UPDATE_PASSWORD.toString()));

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(keycloakRealm)
                .grantType(AuthorizationGrantType.CLIENT_CREDENTIALS.getValue())
                .clientId(clientId)
                .clientSecret(secret)
                .build();

        // Get realm
        RealmResource realmResource = keycloak.realm(keycloakRealm);
        UsersResource userRessource = realmResource.users();

        Response respone = userRessource.create(user);
        if(respone.getStatus() != 201 )
            throw new RuntimeException(respone.getStatusInfo().getReasonPhrase());

    }

    @Override
    public Page<Partner> getAll(Pageable pageable) {
        return partnerRepository.findAll(pageable);
    }

    @Override
    public Partner update(Partner partner) {
        return partnerRepository.save(partner);
    }

    @Override
    public Page<Partner> search(String nom, EtatPartner etatPartner, Pageable pageable) {
        Specification<Partner> specification = null;
        if(nom !=null )
            specification = PartnerSpecification.getPartnerNomLike(nom);
        if(etatPartner !=null)
            specification = PartnerSpecification.addSpecification(specification, PartnerSpecification.getPartnerEtat(etatPartner));

        return partnerRepository.findAll(specification,pageable);
    }


    @Override
    public Boolean deleteClient(Long id) {
        partnerRepository.deleteById(id);
        return true;
    }
}
