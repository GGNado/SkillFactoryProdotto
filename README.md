
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

## Struttura del Progetto

Il progetto è suddiviso nei seguenti componenti:

- classe controller (ProdottiRouter): che si gestice tutte le API.
- classe repository(RepoProdotto): che estende JPArepository ovvero una interfaccia di Spring data JPA che sfruttando Hibernate ci permette di gestire tutte le operazione CRUD automaticamente.
- classe model(Prodotto): che raprresenta la nosta identita e attraverso l'utilizzo di annotazioni ci crea in automatico le tabelle su un qualsiasi database a nostra scelta, quindi abbiamo libera scelta di utilizzare un DBMS qualsiasi senza dover cambiare il codice

## API REST

L' applicazione risponde alle seguenti API REST :

- */upsert*: Aggiunge il prodotto al database.
- */upsertRed*: Aggiunge il prodotto al database con redirect alla tabella.
- */getProdotti*: Mostra i prodotti.
- */deleteProd*: elimina i prodotti .
- */editProd*: modifica i prodotti .

## Configurazione

Per eseguire l'applicazione in locale, è necessario configurare un database MySQL e fornire le credenziali nel file application.properties .

~~~
spring.datasource.url=jdbc:mysql://localhost:3306/skillfactory
spring.datasource.username=root
spring.datasource.password=password
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
## Link Utili

- [Codice](https://github.com/GGNado/SkillFactoryProdotto/tree/main)
- PowerPoint
- [Luigi Massa](https://www.instagram.com/just.luii_/)
- Musella
