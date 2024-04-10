package pkg.skillfactoryspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Prodotti")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincrementa
    private Integer id;
    @Column(length = 30, nullable = false, unique = true)
    private String nome;
    @Column
    private Integer qnt;
    @Column(length = 200, nullable = false)
    private String descrizione;
    @Column(nullable = false)
    private Double prezzo;
}


