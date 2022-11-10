package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.DTO.WordsCreationDto;
import org.arjunaoverdrive.app.model.Word;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sets/add")
public class AddSetController {

    @ModelAttribute("words")
    public WordsCreationDto words() {
        WordsCreationDto words = new WordsCreationDto();
        for (int i = 0; i < 5; i++) {
            words.addWord(new Word());
        }
        return words;
    }

    @GetMapping()
    public String addSetPage() {
        return "add_set";
    }


    // TODO: 02.11.2022 add logic that removes duplicates if a word has errorRateGreaterThan 1 in both languages 
}
