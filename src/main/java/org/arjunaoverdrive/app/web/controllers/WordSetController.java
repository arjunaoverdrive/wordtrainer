package org.arjunaoverdrive.app.web.controllers;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.WordSetServiceImpl;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.web.dto.WordSetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/sets")
public class WordSetController {

    private final WordSetService wordSetService;
    private final UserService userService;

    @Autowired
    public WordSetController(WordSetServiceImpl wordSetServiceImpl, UserService userService) {
        this.wordSetService = wordSetServiceImpl;
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User user(){
        return userService.getUserFromSecurityContext();
    }

    @PreAuthorize("hasAuthority('set:read')")
    @GetMapping("/{id}")
    public String getSetPage(@PathVariable("id") Integer id, Model model) {
        WordSet words = wordSetService.findById(id);
        model.addAttribute("words", words);
        model.addAttribute("title", words.getName());
        model.addAttribute("id", id);
        return "/setpage";
    }

    @PostMapping("/save")
    public String saveSet(@ModelAttribute("words") WordSetDto wordSetDto){
        wordSetService.save(wordSetDto, user());
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('set:write')")
    @PostMapping("/save/{id}")
    public String updateSet(@PathVariable("id")Integer id, @ModelAttribute("words") WordSet wordSet){
        if(wordSetService.update(wordSet, user())){
            return "redirect:/sets/" + id;
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('set:write')")
    @PostMapping(value = "/delete/{id}")
    public String deleteSet(@PathVariable("id") Integer id){
        wordSetService.deleteSet(id);
        return "redirect:/";
    }
}
