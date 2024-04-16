package pkg.skillfactoryspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pkg.skillfactoryspring.database.RepoAccount;
import pkg.skillfactoryspring.database.RepoProdotto;
import pkg.skillfactoryspring.model.Account;
import pkg.skillfactoryspring.model.Prodotto;

import java.util.List;

@RequestMapping("/prodotti")
@Controller
public class ProdottiRouter {
    @Autowired
    RepoProdotto repoProdotto;

    @PostMapping("/upsert")
    public String upsert(Prodotto prodotto, Model model){
        repoProdotto.save(prodotto);
        List<Prodotto> prodotti = repoProdotto.findAll();
        model.addAttribute("prodotti", prodotti);
        return "tabellaProdotti";
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
