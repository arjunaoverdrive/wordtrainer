package org.arjunaoverdrive.app.web.controllers.user;

import org.arjunaoverdrive.app.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user/")
public class UserAccountController {

    private final UserService userService;

    @Autowired
    public UserAccountController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("{id}")
    public String accountPage(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.getUserFromSecurityContext());
        return "user_page";
    }
}
