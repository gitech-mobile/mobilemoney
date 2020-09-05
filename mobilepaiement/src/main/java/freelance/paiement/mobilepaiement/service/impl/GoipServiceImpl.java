package freelance.paiement.mobilepaiement.service.impl;

import freelance.paiement.mobilepaiement.service.GoipService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.StringJoiner;

@Service
public class GoipServiceImpl implements GoipService {

    @Value("${goip.baseUrl}")
    private String baseUrl ;
    @Value("${goip.line}")
    private String goipLine ;
    @Value("${goip.user}")
    private String goipUser  ;
    @Value("${goip.passwd}")
    private  String goipPass ;

    @Override
    public String send(String request, Long id) throws Exception {
        StringJoiner sj = new StringJoiner("&");
        sj.add("/default/en_US/ussd_info.html?type=usdd").add("line1=" + goipLine).add("smskey=" + id.toString()).add("action=USSD").add("telnum=" + request).add("send=Send");
        return callGoip(sj);
    }

    @Override
    public String status() throws Exception {
        StringJoiner sj = new StringJoiner("&");
        sj.add("/default/en_US/send_status.xml?u="+goipUser).add("p="+goipPass);
        return callGoip(sj);
    }

    @Override
    public String exit(Long id) throws Exception {
        StringJoiner sj = new StringJoiner("&");
        sj.add("/default/en_US/ussd_info.html?type=usdd").add("line1="+goipLine).add("smskey=" + id.toString()).add("action=USET").add("telnum=").add("send=Disconnect");
        return callGoip(sj);
    }

    private String callGoip(StringJoiner sj) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBasicAuth(goipUser,goipPass);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        URI uri = URI.create(baseUrl + sj.toString().replace("#","%23"));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri,requestEntity,String.class);
        return responseEntity.getBody();
    }
}
