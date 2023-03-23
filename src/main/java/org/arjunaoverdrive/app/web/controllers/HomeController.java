package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class HomeController {

    private final WordSetService wordSetService;

    @Autowired
    public HomeController(WordSetServiceImpl wordSetServiceImpl) {
        this.wordSetService = wordSetServiceImpl;
    }

    @ModelAttribute("sets")
    public List<WordSet>wordSetList(){
        return wordSetService.findAllRecent();
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

}
