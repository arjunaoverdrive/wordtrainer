package org.arjunaoverdrive.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/sets")
@Slf4j
public class WordSetController {

    private final WordSetService wordSetService;

    @Autowired
    public WordSetController(WordSetServiceImpl wordSetServiceImpl) {
        this.wordSetService = wordSetServiceImpl;
    }

    @GetMapping("/{id}")
    public String getSetPage(@PathVariable("id") Integer id, Model model) {
        WordSet words = wordSetService.findById(id);
        model.addAttribute("words", words);
        model.addAttribute("title", words.getName());
        model.addAttribute("id", id);
        return "/setpage";
    }

    @PostMapping("/save")
    public String saveSet(@ModelAttribute("words") WordSet wordSet){
        wordSetService.save(wordSet);
        return "redirect:/";
    }

    @PostMapping("/save/{id}")
    public String updateSet(@PathVariable("id")Integer id, @ModelAttribute("words") WordSet wordSet){

        if(wordSetService.update(wordSet)){
            return "redirect:/sets/" + id;
        }
        return "redirect:/";
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteSet(@PathVariable("id") Integer id){
        wordSetService.deleteSet(id);
        return "redirect:/";
    }

}
