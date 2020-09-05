package freelance.paiement.donne.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApiResponse {
    private int status;
    private String message;
    private Object reponse;
}
