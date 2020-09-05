package Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Client {
    private Long id;
    private String adresse;
    private String numeroCompte;
    private String departement;
    private String cin;
    private Date finCin;
    private String nom;
    private String pays;
    private String prenom;
    private String region;
    private List<Paiement> paiements;
}
