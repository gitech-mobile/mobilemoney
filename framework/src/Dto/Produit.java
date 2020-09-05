package Dto;
import Enum.EtatProduit;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Produit{
    private Long id;
    private String details;
    private EtatProduit etat;
    private String identifiant;
    private Float montant;
    private String nom;
    private Integer quantite;
}
