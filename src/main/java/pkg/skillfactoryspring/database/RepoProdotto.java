package pkg.skillfactoryspring.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pkg.skillfactoryspring.model.Prodotto;

@Repository
public interface RepoProdotto extends JpaRepository<Prodotto, Integer> {
    public Prodotto findById(int id);
}
