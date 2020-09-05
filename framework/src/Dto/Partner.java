package Dto;

import Enum.EtatPartner;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Partner {
    private Long id;
    private String nom;
    private String adresse;
    private Photo logo;
    private EtatPartner etat;
    private List<Paiement> paiements;
    private Contrat contrat;
}
