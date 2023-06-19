package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.services.PracticeService;
import org.arjunaoverdrive.app.web.dto.PracticeSetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/practice")
public class PracticeController {

    private final PracticeService practiceService;

    @Autowired
    public PracticeController(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @PreAuthorize("hasAuthority('set:read')")
    @GetMapping("/{id}")
    public String practice(@PathVariable("id")Integer setId, Model model){
        PracticeSetDto dto = practiceService.getPracticeSetDto(setId);
        model.addAttribute("words" , dto.getWordList());
        model.addAttribute("id", setId);
        model.addAttribute("source", dto.getSourceLanguageLocale());
        model.addAttribute("target", dto.getTargetLanguageLocale());
        return "practice";
    }

}
