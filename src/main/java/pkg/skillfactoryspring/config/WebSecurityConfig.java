package pkg.skillfactoryspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pkg.skillfactoryspring.database.RepoAccount;
import pkg.skillfactoryspring.model.Account;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    RepoAccount ra;
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
                    .password(u.getPassword())
                    .build();
            usersAuth.add(user);
        }

        return new InMemoryUserDetailsManager(usersAuth);
    }

}
