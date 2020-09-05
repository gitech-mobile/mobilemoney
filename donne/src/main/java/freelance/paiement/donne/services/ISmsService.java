package freelance.paiement.donne.services;

import freelance.paiement.donne.models.Sms;
import org.springframework.transaction.annotation.Transactional;

public interface ISmsService {
    @Transactional
    public void saveSms(Sms sms);
}
