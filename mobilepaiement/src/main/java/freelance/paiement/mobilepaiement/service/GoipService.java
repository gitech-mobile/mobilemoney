package freelance.paiement.mobilepaiement.service;

import java.io.UnsupportedEncodingException;

public interface GoipService {
     String send(String request, Long id) throws Exception;
     String status() throws Exception;
     String exit(Long id) throws Exception;
}
