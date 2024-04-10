package pkg.skillfactoryspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pkg.skillfactoryspring.database.RepoAccount;
import pkg.skillfactoryspring.model.Account;

import java.util.List;

@RequestMapping("/avvio")
@Controller
public class Avvio {
    // VARIABILE DI APPOGGIO PER LA REPO
    // Springboot crea oggetto di classe anonima
    // che risulta figlia di questa interfaccia iniettando l'oggeto di tipo account
    @Autowired
    RepoAccount repoAccount;

    @PostMapping("/takeValues")
    public String takeValues(Account account){
        System.out.println(account.getUsername());
        System.out.println(account.getPassword());

        return "form";
    }

    @PostMapping("/upsert")
    public String upsert(Account account){
        repoAccount.save(account);
        return "home";
    }

    @GetMapping("/getAccounts")
    public String getAccounts(Model model){
        List<Account> accounts = repoAccount.findAll();
        model.addAttribute("accounts", accounts);
        return "tabella";
    }

    @GetMapping("/deleteAccount")
    public String deleteAccount(@RequestParam Integer id){
        repoAccount.deleteById(id);
        return "home";
    }

    @GetMapping("/editAccount")
    public String editAccount(@RequestParam int id, Model model){
        Account account = repoAccount.findById(id);
        model.addAttribute("account", account);
        return "formEdit";
    }
}
