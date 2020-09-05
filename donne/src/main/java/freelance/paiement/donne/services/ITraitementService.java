package freelance.paiement.donne.services;

import freelance.paiement.donne.exceptions.CustomException;
import freelance.paiement.donne.models.Sms;

public interface ITraitementService {
    public void traitementSms(Sms sms) throws CustomException;
}
