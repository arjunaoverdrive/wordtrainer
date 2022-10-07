package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.DTO.WordsCreationDto;
import org.arjunaoverdrive.app.model.Word;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AddSetController {

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
