package org.arjunaoverdrive.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DTO.MistakenWordsDto;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/practice")
@Slf4j
public class PracticeController {

    private final WordSetService wordSetService;

    @Autowired
    public PracticeController(WordSetServiceImpl wordSetServiceImpl) {
        this.wordSetService = wordSetServiceImpl;
    }

    @GetMapping("/{id}")
    public String practice(@PathVariable("id")Integer id, Model model){
        model.addAttribute("words" , wordSetService.getWordList(id));
        model.addAttribute("id", id);
        log.info("practice words set " + id);
        return "practice";
    }

    @PostMapping("/mistaken")
    public String mistakenWords(MistakenWordsDto words, Model model){
        model.addAttribute("words", words.getWords());
        return "mistaken_words";
    }
}
