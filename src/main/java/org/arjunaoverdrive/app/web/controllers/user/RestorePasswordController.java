package org.arjunaoverdrive.app.web.controllers.user;

import org.arjunaoverdrive.app.services.user.RestorePasswordService;
import org.arjunaoverdrive.app.web.dto.user.EmailDto;
import org.arjunaoverdrive.app.web.dto.user.PasswordRestoreStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/restore-password")
public class RestorePasswordController {
    private final RestorePasswordService restorePasswordService;

    @Autowired
    public RestorePasswordController(RestorePasswordService restorePasswordService) {
        this.restorePasswordService = restorePasswordService;
    }

    @ModelAttribute("emailDto")
    public EmailDto emailDto() {
        return new EmailDto();
    }

    @ModelAttribute("response")
    public PasswordRestoreStatus passwordRestoreStatus() {
        return new PasswordRestoreStatus();
    }

    @GetMapping
    public String restore( ) {
        return "restore_password";
    }

    @PostMapping("/restore")
    public String restore(EmailDto emailDto) {
        PasswordRestoreStatus response = restorePasswordService.restorePasswordForUser(emailDto.getEmail());
        if (!response.isStatus()){
            return "redirect:/restore-password?error";
        }
        return "redirect:/restore-password?success";
    }

}
