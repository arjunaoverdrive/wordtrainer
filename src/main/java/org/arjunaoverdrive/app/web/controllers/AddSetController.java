package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.web.dto.WordSetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sets/add")
public class AddSetController {

    private final UserService userService;

    @Autowired
    public AddSetController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("words")
    public WordSetDto words() {
        WordSetDto words = new WordSetDto();
        for (int i = 0; i < 5; i++) {
            words.addWord(new Word());
        }
        return words;
    }

    @ModelAttribute("user")
    public User user(){
        return userService.getUserFromSecurityContext();
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('set:write')")
    public String addSetPage() {
        return "add_set";
    }

}
