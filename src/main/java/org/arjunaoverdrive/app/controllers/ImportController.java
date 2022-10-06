package org.arjunaoverdrive.app.controllers;

import org.arjunaoverdrive.app.DTO.ImportDto;
import org.arjunaoverdrive.app.services.ImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ImportController {

    @Autowired
    private final ImportServiceImpl importServiceImpl;

    public ImportController(ImportServiceImpl importServiceImpl) {
        this.importServiceImpl = importServiceImpl;
    }

    @ModelAttribute("importDto")
    public ImportDto importDto() {
        return new ImportDto();
    }

    @GetMapping("/import")
    public String importSet() {
        return "import_set";
    }

    @PostMapping("/import")
    public String importWords(ImportDto importDto){
        importServiceImpl.importSet(importDto);
        return "redirect:/";
    }
}
