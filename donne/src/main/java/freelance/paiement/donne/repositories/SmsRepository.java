package freelance.paiement.donne.repositories;

import freelance.paiement.donne.models.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository<Sms, Long> {
}
