package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetPageService;
import org.arjunaoverdrive.app.services.WordSetPageServiceImpl;
import org.arjunaoverdrive.app.services.wordset.WordSetService;
import org.arjunaoverdrive.app.services.wordset.WordSetServiceImpl;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.web.dto.WordSetResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final WordSetPageService wordSetPageService;
    private final UserService userService;

    @Autowired
    public HomeController(WordSetPageService wordSetPageService, UserService userService) {
        this.wordSetPageService = wordSetPageService;
        this.userService = userService;
    }

    @ModelAttribute("sets")
    public List<WordSetResponseDto>wordSetList(){
        return wordSetPageService.findWordSets();
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
