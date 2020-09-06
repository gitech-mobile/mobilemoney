package Dto;
import Enum.TypeContrat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Contrat {
    private Long id;
    private TypeContrat type;
    private Date date;
    private Date dateFin;
    private byte[] contrat;
    private Partner partner;
}
