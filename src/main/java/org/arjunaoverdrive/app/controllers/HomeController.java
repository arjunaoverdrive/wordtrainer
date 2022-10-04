package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.ImportService;
import org.arjunaoverdrive.app.services.WordSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private final WordSetService wordSetService;

    @Autowired
    public HomeController(WordSetService wordSetService) {
        this.wordSetService = wordSetService;
    }

    @ModelAttribute("sets")
    public List<WordSet>wordSetList(){
        return wordSetService.findAll();
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

}
