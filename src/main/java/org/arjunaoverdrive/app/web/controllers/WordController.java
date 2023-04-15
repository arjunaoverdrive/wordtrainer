package org.arjunaoverdrive.app.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sets")
@Slf4j
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping("/{setid}/words/delete/{id}")
    @PreAuthorize("hasAuthority('set:write')")
    public String deleteWord(@PathVariable("setid")Integer setId, @PathVariable("id") Long id){
        wordService.deleteWord(id);
        log.info("delete word " + id);
        return "redirect:/sets/" + setId;
    }
}
