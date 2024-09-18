package pkg.skillfactoryspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pkg.skillfactoryspring.database.RepoAccount;
import pkg.skillfactoryspring.database.RepoRole;
import pkg.skillfactoryspring.model.Account;
import pkg.skillfactoryspring.model.Role;
import pkg.skillfactoryspring.utility.Crittografia;

@Configuration
public class CheckRecords {
    @Autowired
    RepoAccount repoAccount;
    @Autowired
    RepoRole repoRole;
    @Autowired
    Crittografia cripto;

    @Bean //Metodo che quando si avvia il programma, questo metodo viene eseguito per primo
    String getDefaultUser() {
        Role role = getDefaultRoles();
        System.out.println("Controllo in corso...");
        if (repoAccount.findAll().isEmpty()){
            Account account = new Account();
            account.setEmail("root");
            account.setPassword(cripto.encrypt("root"));
            account.setRole(role);
            System.out.println("Aggiungo account di default...");
            repoAccount.save(account);
        } else {
            System.out.println("Gli accounti sono stati trovati");
        }

        return null;
    }

    Role getDefaultRoles(){
        System.out.println("Controllo Ruoli in corso...");
        if (repoRole.findAll().isEmpty()){
            Role admin = new Role();
            Role user = new Role();
            admin.setNome("ADMIN");
            user.setNome("USER");
            repoRole.save(admin);
            repoRole.save(user);
            return admin;

        } else {
            System.out.println("Gli accounti sono stati trovati");
        }

        return null;
    }
}
