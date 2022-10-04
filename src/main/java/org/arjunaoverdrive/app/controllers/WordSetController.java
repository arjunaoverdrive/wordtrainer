package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/sets")
public class WordSetController {

    private final WordSetService wordSetService;

    @Autowired
    public WordSetController(WordSetService wordSetService) {
        this.wordSetService = wordSetService;
    }

    @GetMapping("/{id}")
    public String getSetPage(@PathVariable("id") Integer id, Model model) {
        WordSet words = wordSetService.findById(id);
        model.addAttribute("words", words);
        model.addAttribute("title", words.getName());
        model.addAttribute("id", id);
        return "/setpage";
    }

    @PostMapping("/save/{id}")
    public String updateSet(@PathVariable("id")Integer id, @ModelAttribute("words") WordSet wordSet){
        wordSetService.save(wordSet);

        return "redirect:/sets/" + id;
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteSet(@PathVariable("id")Integer id){
        wordSetService.deleteSet(id);
        return "redirect:/";
    }

    // TODO: 04.10.2022 add features: Typo, change language, persist results in db, add set, choose set, recent sets 
}
