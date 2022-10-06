package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private final WordSetServiceImpl wordSetServiceImpl;

    @Autowired
    public HomeController(WordSetServiceImpl wordSetServiceImpl) {
        this.wordSetServiceImpl = wordSetServiceImpl;
    }

    @ModelAttribute("sets")
    public List<WordSet>wordSetList(){
        return wordSetServiceImpl.findAll();
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

}
