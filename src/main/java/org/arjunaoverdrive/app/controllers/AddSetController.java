package org.arjunaoverdrive.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DTO.WordsCreationDto;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.services.WordService;
import org.arjunaoverdrive.app.services.WordSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sets/add")
@Slf4j
public class AddSetController {

    private final WordService wordService;
    private final WordSetService wordSetService;

    @Autowired
    public AddSetController(WordService wordService, WordSetService wordSetService) {
        this.wordService = wordService;
        this.wordSetService = wordSetService;
    }

    @ModelAttribute("words")
    public WordsCreationDto words(){
        WordsCreationDto words = new WordsCreationDto();
        for(int i = 0; i < 5; i++){
            words.addWord(new Word());
        }
        log.info("return 5 empty words");
        return words;
    }

    @GetMapping()
    public String addSetPage() {
        return "add_set";
    }


    // TODO: 02.11.2022 add logic that removes duplicates if a word has errorRateGreaterThan 1 in both languages 
}
