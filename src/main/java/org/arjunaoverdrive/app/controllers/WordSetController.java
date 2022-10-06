package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/sets")
public class WordSetController {

    private final WordSetServiceImpl wordSetServiceImpl;

    @Autowired
    public WordSetController(WordSetServiceImpl wordSetServiceImpl) {
        this.wordSetServiceImpl = wordSetServiceImpl;
    }

    @GetMapping("/{id}")
    public String getSetPage(@PathVariable("id") Integer id, Model model) {
        WordSet words = wordSetServiceImpl.findById(id);
        model.addAttribute("words", words);
        model.addAttribute("title", words.getName());
        model.addAttribute("id", id);
        return "/setpage";
    }

    @PostMapping("/save")
    public String saveSet(@ModelAttribute("words") WordSet wordSet){
        wordSetServiceImpl.save(wordSet);
        return "redirect:/";
    }

    @PostMapping("/save/{id}")
    public String updateSet(@PathVariable("id")Integer id, @ModelAttribute("words") WordSet wordSet){
        wordSetServiceImpl.update(wordSet);

        return "redirect:/sets/" + id;
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteSet(@PathVariable("id") Integer id){
        wordSetServiceImpl.deleteSet(id);
        return "redirect:/";
    }

    // TODO: 04.10.2022 add features:
    //  Typo
    //  change language
    //  persist results in db
    //  recent sets
}
