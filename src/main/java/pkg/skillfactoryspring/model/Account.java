package pkg.skillfactoryspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Mapping = Annotare la classe in modo che Hibernate faccia il DDL in automatico
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincrementa
    private Integer id;
    @Column(length = 30, nullable = false, unique = true)
    private String username;
    @Column(length = 30, nullable = false)
    private String password;
}

