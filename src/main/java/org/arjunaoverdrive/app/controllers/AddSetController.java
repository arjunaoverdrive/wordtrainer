package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.DTO.WordsCreationDto;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddSetController {

    private final WordSetService wordSetService;

    @Autowired
    public AddSetController(WordSetService wordSetService) {
        this.wordSetService = wordSetService;
    }

    @GetMapping("/add")
    public String addSetPage(Model model) {
        WordsCreationDto words = new WordsCreationDto();

        for(int i = 0; i < 5; i++){
            words.addWord(new Word());
        }

        model.addAttribute("words", words);
        return "add_set";
    }

    @PostMapping("/sets/save")
    public String saveSet(@ModelAttribute("words")WordSet wordSet){
        wordSetService.save(wordSet);
        return "redirect:/";
    }
}
