package pkg.skillfactoryspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pkg.skillfactoryspring.database.RepoAccount;
import pkg.skillfactoryspring.model.Account;
import pkg.skillfactoryspring.utility.Crittografia;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    RepoAccount ra;
    @Autowired
    Crittografia cripto;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","home", "/avvio/upsert").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        List<UserDetails> usersAuth = new ArrayList<UserDetails>();
        List<Account> users = ra.findAll();
        for(Account u:users) {
            //Vengono caricati tutti gli utenti registrati per
            //autorizzarli all'accesso
            UserDetails user = User.withDefaultPasswordEncoder()
                    .username(u.getUsername())
                    .password(cripto.decrypt(u.getPassword()))
                    .roles(u.getRole().getNome())
                    .build();
            usersAuth.add(user);
            System.out.println(user.getUsername() + " " + user.getAuthorities());
        }

        return new InMemoryUserDetailsManager(usersAuth);
    }

}
