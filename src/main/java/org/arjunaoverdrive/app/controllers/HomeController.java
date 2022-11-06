package org.arjunaoverdrive.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
public class HomeController {

    private final WordSetService wordSetService;

    @Autowired
    public HomeController(WordSetServiceImpl wordSetServiceImpl) {
        this.wordSetService = wordSetServiceImpl;
    }

    @ModelAttribute("sets")
    public List<WordSet>wordSetList(){
        log.info("return recent sets");
        return wordSetService.findAllRecent();
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

}
