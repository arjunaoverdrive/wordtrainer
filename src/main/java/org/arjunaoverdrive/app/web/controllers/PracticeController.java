package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.web.DTO.MistakenWordsDto;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/practice")
public class PracticeController {

    private final WordSetService wordSetService;

    @Autowired
    public PracticeController(WordSetServiceImpl wordSetServiceImpl) {
        this.wordSetService = wordSetServiceImpl;
    }

    @PreAuthorize("hasAuthority('set:read')")
    @GetMapping("/{id}")
    public String practice(@PathVariable("id")Integer id, Model model){
        model.addAttribute("words" , wordSetService.getWordList(id));
        model.addAttribute("id", id);
        return "practice";
    }

    @PreAuthorize("hasAuthority('set:read')")
    @PostMapping("/mistaken")
    public String mistakenWords(MistakenWordsDto words, Model model){
        model.addAttribute("words", words.getShuffledWords());
        return "mistaken_words";
    }
}