package freelance.paiement.donne.services.Impl;

import freelance.paiement.donne.models.Sms;
import freelance.paiement.donne.repositories.SmsRepository;
import freelance.paiement.donne.services.ISmsService;
import org.springframework.stereotype.Service;

@Service
public class SmsService implements ISmsService {
    private final SmsRepository smsRepository;

    public SmsService(SmsRepository smsRepository) {
        this.smsRepository = smsRepository;
    }

    @Override
    public void saveSms(Sms sms) {
    smsRepository.save(sms);
    }
}
