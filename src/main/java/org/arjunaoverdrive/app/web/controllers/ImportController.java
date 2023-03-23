package org.arjunaoverdrive.app.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.web.DTO.ImportDto;
import org.arjunaoverdrive.app.services.ImportService;
import org.arjunaoverdrive.app.services.ImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/api/v1")
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
    @PreAuthorize("hasAuthority('set:write')")
    public String importSet() {
        return "import_set";
    }

    @PostMapping("/import")
    @PreAuthorize("hasAuthority('set:write')")
    public String importWords(ImportDto importDto){
        log.info("import new set " + importDto.getName());
        importService.importSet(importDto);
        return "redirect:/";
    }
}