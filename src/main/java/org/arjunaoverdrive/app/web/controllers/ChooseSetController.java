package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.arjunaoverdrive.app.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/choose")
public class ChooseSetController {

    private final WordSetService wordSetService;
    private final UserService userService;

    @Autowired
    public ChooseSetController(WordSetServiceImpl wordSetService, UserService userService) {
        this.wordSetService = wordSetService;
        this.userService = userService;
    }

    @ModelAttribute("sets")
    public List<WordSet> sets(){
        return this.wordSetService.findAll();
    }

    @ModelAttribute("user")
    public User user(){
        return userService.getUserFromSecurityContext();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('set:read')")
    public String chooseSetPage(){
        return "/choose_set";
    }
}
