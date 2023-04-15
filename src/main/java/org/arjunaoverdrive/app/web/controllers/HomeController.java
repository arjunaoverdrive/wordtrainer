package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.arjunaoverdrive.app.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final WordSetService wordSetService;
    private final UserService userService;

    @Autowired
    public HomeController(WordSetServiceImpl wordSetServiceImpl, UserService userService) {
        this.wordSetService = wordSetServiceImpl;
        this.userService = userService;
    }

    @ModelAttribute("sets")
    public List<WordSet>wordSetList(){
        return wordSetService.findAllRecent(user());
    }

    @ModelAttribute("user")
    public User user(){
        return userService.getUserFromSecurityContext();
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

}
