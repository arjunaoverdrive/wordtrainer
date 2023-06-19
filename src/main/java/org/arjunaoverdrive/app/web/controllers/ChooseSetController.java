package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetPageService;
import org.arjunaoverdrive.app.services.wordset.WordSetServiceImpl;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.web.dto.WordSetResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/choose")
public class ChooseSetController {

    private final WordSetPageService wordSetPageService;
    private final UserService userService;

    @Autowired
    public ChooseSetController( WordSetPageService wordSetPageService, UserService userService) {
        this.wordSetPageService = wordSetPageService;
        this.userService = userService;
    }

    @ModelAttribute("sets")
    public List<WordSetResponseDto> sets(){
        return this.wordSetPageService.findWordSets();
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
