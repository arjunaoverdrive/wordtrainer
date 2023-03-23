package org.arjunaoverdrive.app.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.web.DTO.WordsL2List;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/api/v1")
public class MistakenWordsController {

    private final WordService wordService;


    @Autowired
    public MistakenWordsController(WordService wordService) {
        this.wordService = wordService;
    }

    @PreAuthorize("hasAuthority('set:read')")
    @GetMapping("/problematicWords")
    public String mistakenWordsPage(@RequestParam Integer setId, @RequestParam Integer lang, Model model){
        model.addAttribute("words", wordService.getWordListWithProblematicWords(setId, lang));
        model.addAttribute("lang", lang);
        model.addAttribute("setId", setId);
        log.info("return mistaken words. set id: " + setId + " lang: " + lang);
        return "problematic_words";
    }

    @PreAuthorize("hasAuthority('set:read')")
    @GetMapping("/problematicWords/l2")
    @ResponseBody
    public ResponseEntity<WordsL2List> mistakenWordsLang2AllSets(@RequestParam Integer lang, @RequestParam(required = false) Integer setId, Model model){
        List<Word> l2words = wordService.getLang2Words(setId, lang);
        model.addAttribute("l2words", l2words);
        log.info("return lang 2 words. set id: " + setId + " lang 2: " + lang);
        return ResponseEntity.ok(new WordsL2List(l2words));
    }
}
