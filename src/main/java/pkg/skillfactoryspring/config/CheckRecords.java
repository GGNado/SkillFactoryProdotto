package pkg.skillfactoryspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pkg.skillfactoryspring.database.RepoAccount;
import pkg.skillfactoryspring.model.Account;
import pkg.skillfactoryspring.utility.Crittografia;

@Configuration
public class CheckRecords {
    @Autowired
    RepoAccount repoAccount;
    @Autowired
    Crittografia cripto;

    @Bean //Metodo che quando si avvia il programma, questo metodo viene eseguito per primo
    String getDefaultUser() {
        System.out.println("Controllo in corso...");
        if (repoAccount.findAll().isEmpty()){
            Account account = new Account();
            account.setUsername("root");
            account.setPassword(cripto.encrypt("root"));
            System.out.println("Aggiungo account di default...");
            repoAccount.save(account);
        } else {
            System.out.println("Gli accounti sono stati trovati");
        }

        return null;
    }
}
