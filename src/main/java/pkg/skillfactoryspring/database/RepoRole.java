package pkg.skillfactoryspring.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pkg.skillfactoryspring.model.Account;
import pkg.skillfactoryspring.model.Role;

@Repository
public interface RepoRole extends JpaRepository<Role, Integer> {
    public Role findById(int id);
}
