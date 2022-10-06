package org.arjunaoverdrive.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/practice")
@Slf4j
public class PracticeController {

    private final WordSetServiceImpl wordSetServiceImpl;

    @Autowired
    public PracticeController(WordSetServiceImpl wordSetServiceImpl) {
        this.wordSetServiceImpl = wordSetServiceImpl;
    }

    @GetMapping("{id}")
    public String practice(@PathVariable("id")Integer id, Model model){
        model.addAttribute("words" , wordSetServiceImpl.getWordList(id));
        return "practice";
    }
}
