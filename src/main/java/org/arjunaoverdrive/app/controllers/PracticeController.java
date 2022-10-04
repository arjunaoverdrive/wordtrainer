package org.arjunaoverdrive.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.services.WordSetService;
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
    public PracticeController(WordSetService wordSetService) {
        this.wordSetService = wordSetService;
    }

    @GetMapping("{id}")
    public String practice(@PathVariable("id")Integer id, Model model){
        model.addAttribute("words" ,wordSetService.getWordList(id));
        return "practice";
    }
}
