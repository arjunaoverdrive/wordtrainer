package org.arjunaoverdrive.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DTO.ImportDto;
import org.arjunaoverdrive.app.services.ImportService;
import org.arjunaoverdrive.app.services.ImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ImportController {

    @Autowired
    private final ImportService importService;

    public ImportController(ImportServiceImpl importServiceImpl) {
        this.importService = importServiceImpl;
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
        log.info("import new set " + importDto.getName());
        importService.importSet(importDto);
        return "redirect:/";
    }
}
