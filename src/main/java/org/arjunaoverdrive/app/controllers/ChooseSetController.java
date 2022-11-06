package org.arjunaoverdrive.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/choose")
public class ChooseSetController {

    private final WordSetService wordSetService;

    public ChooseSetController(WordSetServiceImpl wordSetService) {
        this.wordSetService = wordSetService;
    }

    @ModelAttribute("sets")
    public List<WordSet> sets(){
        log.info("return all sets");
        return this.wordSetService.findAll();
    }

    @GetMapping
    public String chooseSetPage(){
        return "/choose_set";
    }
}
