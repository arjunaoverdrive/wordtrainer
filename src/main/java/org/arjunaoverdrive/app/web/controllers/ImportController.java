package org.arjunaoverdrive.app.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.services.ImportService;
import org.arjunaoverdrive.app.services.ImportServiceImpl;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.web.dto.ImportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ImportController {

    private final ImportService importService;
    private final UserService userService;

    @Autowired
    public ImportController(ImportServiceImpl importServiceImpl, UserService userService) {
        this.importService = importServiceImpl;
        this.userService = userService;
    }

    @ModelAttribute("importDto")
    public ImportDto importDto() {
        return new ImportDto();
    }

    @ModelAttribute("user")
    public User user(){
        return userService.getUserFromSecurityContext();
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
        importService.importSet(importDto, user());
        return "redirect:/";
    }
}
