package org.arjunaoverdrive.app.web.controllers.user;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.web.dto.user.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/")
public class UserAccountController {

    private final UserService userService;


    @Autowired
    public UserAccountController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User user(){
        return userService.getUserFromSecurityContext();
    }

    @GetMapping("{id}")
    public String userPage(@PathVariable("id") Integer id){
        return "account_settings";
    }

    @PostMapping("{id}/save-changes")
    public String saveChanges(@PathVariable("id") Integer id, @RequestBody AccountDto dto){
        userService.saveChanges(id, dto);
        return "account_settings";
    }



}
