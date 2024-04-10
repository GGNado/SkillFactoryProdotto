package pkg.skillfactoryspring.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pkg.skillfactoryspring.model.Account;

/* Metodi più utili
* save(Entità) upsert
* findById(ID)
* findAll() dammi tutto
* deleteByID(ID)
* existsByID(ID)
*/
@Repository
public interface RepoAccount extends JpaRepository<Account, Integer> {
    public Account findById(int id);
}
