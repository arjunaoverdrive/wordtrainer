package org.arjunaoverdrive.app.web.controllers.user;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.web.dto.user.RegisterForm;
import org.arjunaoverdrive.app.services.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
@Slf4j
public class RegistrationController {
    private final UserServiceImpl userService;

    @Autowired
    public RegistrationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signup(Model model){
        model.addAttribute("registerForm", new RegisterForm());
        return "registration";
    }

    @PostMapping("/signup")
    public String register(@ModelAttribute("user") RegisterForm registerForm){
        try{
            long id = userService.save(registerForm);
            log.info("saved user " + registerForm.getEmail());
        }
        catch (Exception e){
            log.warn(String.format("Cannot sign up with these %s credentials.\n", registerForm) + e.getMessage());
            return "redirect:/registration?error";
        }
        return "redirect:/login";
    }
}
