package org.arjunaoverdrive.app.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.services.WordService;
import org.arjunaoverdrive.app.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping("")
@Deprecated
public class MistakenWordsController {

    private final UserService userService;


    @Autowired
    public MistakenWordsController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User user(){
        return userService.getUserFromSecurityContext();
    }

    @PreAuthorize("hasAuthority('set:read')")
    @GetMapping("/problematicWords")
    public String mistakenWordsPage(@RequestParam Integer setId, @RequestParam Integer lang, Model model){
        model.addAttribute("lang", lang);
        model.addAttribute("setId", setId);
        log.info("return mistaken words. set id: " + setId + " lang: " + lang);
        return "problematic_words";
    }
}
