# Documentazione dell'Applicazione

## Panoramica

Questo progetto implementa un sistema di gestione prodotti utilizzando Java con il framework Spring. L'applicazione fornisce , la modifica dei dati della tabella , l'eleminazione e l'aggiunta .

## Dipendenze usate
- [thymeleaf](https://www.thymeleaf.org/)
- [Spring data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring data JDBC](https://spring.io/projects/spring-data-jdbc)
- [MySQL DRIVER](https://spring.io/guides/gs/accessing-data-mysql)
- [Spring Web](https://spring.io/projects/spring-ws)
- [Lombok](https://projectlombok.org/)
- [Spring Security](https://spring.io/projects/spring-security)

## Struttura del Progetto

Il progetto è suddiviso nei seguenti package:
![prova](https://i.ibb.co/z7D0wzB/Whats-App-Image-2024-04-11-at-14-58-31.jpg)
- package config: contiene tutte le classi con l'annotazione ```@Configuration```, annotazione che serve a Spring per identificare le classi come configuratore, le quali ci permettono di avere delle configurazioni, come ad esempio controlli di login, controlli sql, ect... che vengono eseguiti ad  ogni avvio della all'applicazione.
- package controller: contiene tutte le classi con annotazione ```@Controller```, annotazione che serve a Spring per identificare le classi come controller di rotte le quali ci permettono di decidere cosa fare sotto determinati API.
- package database: contiene tutte le interfaccie con l'annotazione ```@Repository```, annotazione che serve a Spring per identificare le interfaccie come repository  le quali ci permettono di gestire tutte le operazioni CRUD. Estendono l'interfaccia JPARepository che pernde come argomento l'entita il tipo del ID.
- package model: contiene tutte le classi con annotazione ```@Entity```, annotazione che serve a Spring per identificare le classi come entita le quali ci permettono di rappresentare i nostri oggetti.

## Annotazioni usate
-   ```@Configuration```: Indica che una classe come configuratore

- ```@Autowired```: Springboot crea un oggetto di classe anonima che risulta figlia dell'interfaccia in cui viene utilizzata iniettando l'oggetto di tipo account, prodotto etc.

- ```@Bean```: Indica che un metodo che viene avviato prima di tutti.

- ```@EnableWebSecurity```: Abilita la configurazione di Spring Security per l'applicazione Web.

- ```@RequestMapping("/API")```: Mappa una richiesta HTTP a un metodo di un controller specifico. In questo caso, il mapping viene fatto per tutte le richieste che iniziano con "/API".

- ```@Controller```: Indica che una classe è un controller nel contesto di Spring MVC, responsabile della gestione delle richieste e delle risposte HTTP.

- ```@PostMapping("/API")```: Mappa una richiesta HTTP POST a un metodo di un controller specifico. In questo caso, il mapping viene fatto per le richieste POST che iniziano con "/API".

- ```@GetMapping("/API")```: Mappa una richiesta HTTP GET a un metodo di un controller specifico. In questo caso, il mapping viene fatto per le richieste GET che iniziano con "/API".

- ```@Repository```: Indica che una classe è un componente repository in Spring, utilizzato per l'accesso ai dati. Tipicamente, si usa per interagire con il database.

- ```@Data```: Annotazione di Lombok che genera automaticamente i metodi getter, setter, equals, hashCode e toString per una classe.

- ```@NoArgsConstructor```: Genera un costruttore senza argomenti per una classe.

- ```@AllArgsConstructor```: Genera un costruttore che accetta tutti i campi della classe come argomenti.

- ```@Entity```: Indica che una classe rappresenta un'entità persistente in un database relazionale.

- ```@Table(name = "nomeTabella")```: Specifica il nome della tabella nel database relazionale a cui l'entità è mappata.

- ```@Id```: Indica che un campo è una chiave primaria di un'entità.

- ```@GeneratedValue```:

- ```@Column```: Specifica il mapping tra il campo di un'entità e la colonna corrispondente nel database.


## API REST

L' applicazione risponde alle seguenti API REST :

- *prodotti/upsert*: Aggiunge il prodotto al database.
- *prodotti/upsertRed*: Aggiunge il prodotto al database con redirect alla tabella.
- *prodotti/getProdotti*: Mostra i prodotti.
- *prodotti/deleteProd*: elimina i prodotti .
- *prodotti/editProd*: modifica i prodotti .
- *avvio/upsert*: Aggiunge l' utente al database.
- *avvio/getAccounts*: Mostra gli utenti.
- *avvio/deleteAccounts*: Elimina gli utenti.
- *avvio/EditAccounts*: Modifica gli utenti.


## Configurazione
### Database

Per eseguire l'applicazione,  è necessario configurare un database MySQL e fornire le credenziali nel file application.properties .

~~~
spring.datasource.url=jdbc:mysql://localhost:3306/skillfactory
spring.datasource.username=root
spring.datasource.password=password
~~~
### Sign Up & Sign In
~~~java
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
~~~

### Rotte predefinite
~~~java
@Configuration
public class MvcConfig implements WebMvcConfigurer {
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/form").setViewName("form");
		registry.addViewController("/formProdotti").setViewName("formProdotti");
		registry.addViewController("/login").setViewName("login");
	}
}
~~~
## Esempio di Codice

Classe Prodotto:
```java
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
```
Classe ProdottiRouter
```java
@RequestMapping("/prodotti")
@Controller
public class ProdottiRouter {
    @Autowired
    RepoProdotto repoProdotto;

    @PostMapping("/upsert")
    public String upsert(Prodotto prodotto){
        repoProdotto.save(prodotto);
        return "formProdotti";
    }

    @PostMapping("/upsertRed")
    public String upsertRed(Prodotto prodotto, Model model){
        repoProdotto.save(prodotto);
        List<Prodotto> prodotti = repoProdotto.findAll();
        model.addAttribute("prodotti", prodotti);
        return "tabellaProdotti";
    }

    @GetMapping("/getProdotti")
    public String getAccounts(Model model){
        List<Prodotto> prodotti = repoProdotto.findAll();
        model.addAttribute("prodotti", prodotti);
        return "tabellaProdotti";
    }

    @GetMapping("/deleteProd")
    public String deleteProd(@RequestParam Integer id, Model model){
        repoProdotto.deleteById(id);
        List<Prodotto> prodotti = repoProdotto.findAll();
        model.addAttribute("prodotti", prodotti);
        return "tabellaProdotti";
    }

    @GetMapping("/editProd")
    public String editAccount(@RequestParam int id, Model model){
        Prodotto prodotto = repoProdotto.findById(id);
        model.addAttribute("p", prodotto);
        return "formProdEdit";
    }
}
```
## Anteprima applicazione
### Login
![](https://i.ibb.co/3B3LmBv/Screenshot-2024-04-11-alle-15-49-18.png)
### Registrazione
![](https://i.ibb.co/Mk0DY4s/Screenshot-2024-04-11-alle-15-47-36.png)
### Lista Prodotti
![](https://i.ibb.co/BgNrym0/Screenshot-2024-04-11-alle-15-50-44.png)
## Link Utili

- [Codice](https://github.com/GGNado/SkillFactoryProdotto/tree/main)
- PowerPoint
- [Luigi Massa](https://www.instagram.com/just.luii_/)
- Musella
