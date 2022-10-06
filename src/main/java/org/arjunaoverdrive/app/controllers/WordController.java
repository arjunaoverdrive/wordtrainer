package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.services.WordService;
import org.arjunaoverdrive.app.services.WordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sets")
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordServiceImpl wordServiceImpl) {
        this.wordService = wordServiceImpl;
    }

    @PostMapping("/{setid}/words/delete/{id}")
    public String deleteWord(@PathVariable("setid")Integer setId, @PathVariable("id") Long id){
        wordService.deleteWord(id);
        return "redirect:/sets/" + setId;
    }
}
