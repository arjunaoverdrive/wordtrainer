package org.arjunaoverdrive.app.web.controllers.user;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.web.dto.user.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }
}
