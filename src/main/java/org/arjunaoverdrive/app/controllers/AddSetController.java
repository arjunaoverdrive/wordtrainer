package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.DTO.WordsCreationDto;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AddSetController {

    private final WordSetServiceImpl wordSetServiceImpl;

    @Autowired
    public AddSetController(WordSetServiceImpl wordSetServiceImpl) {
        this.wordSetServiceImpl = wordSetServiceImpl;
    }

    @ModelAttribute("words")
    public WordsCreationDto words(){
        WordsCreationDto words = new WordsCreationDto();
        for(int i = 0; i < 5; i++){
            words.addWord(new Word());
        }
        return words;
    }

    @GetMapping("/add")
    public String addSetPage() {
        return "add_set";
    }
}
