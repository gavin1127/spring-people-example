package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Gavin on 4/14/17.
 */
@Controller
public class PeopleController {
    @Autowired
    PeopleRepository repo;


    @GetMapping("/")
    public String listPeople (Model model, @RequestParam(defaultValue = "") String search){
        model.addAttribute("search", search);
        model.addAttribute("people", repo.listPeople(search));
        model.addAttribute("size", repo.listPeople(search).size());
        return "index";

    }
    @GetMapping("/personForm")
    public String showPersonFrom(Model model, Integer personId){
        if (personId != null){
            model.addAttribute("person", repo.specificPerson(personId));
        }
        else{
            model.addAttribute("person", new Person());
        }
        return "personForm";
    }
    @PostMapping("/savePerson")
    public String showPerson(Person person){
        repo.savePerson(person);
        return "redirect:/";
    }

}