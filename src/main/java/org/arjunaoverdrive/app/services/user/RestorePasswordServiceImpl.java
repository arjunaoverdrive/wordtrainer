package org.arjunaoverdrive.app.services.user;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.services.notifications.NotificationService;
import org.arjunaoverdrive.app.web.dto.user.PasswordRestoreStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class RestorePasswordServiceImpl implements RestorePasswordService{

    private final UserService userService;
    private final NotificationService notificationService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public RestorePasswordServiceImpl(UserService userService, NotificationService notificationService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.notificationService = notificationService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public PasswordRestoreStatus restorePasswordForUser(String email) {
        User user;
        try {
            user = userService.findUserByEmail(email);
            sendEmail(user);
        }catch (Exception e){
            return new PasswordRestoreStatus(false, e.getMessage());
        }
        return new PasswordRestoreStatus(true, "Check your mail! We've sent you temporary password!");
    }

    private void sendEmail(User user) {
        String password = generatePassword();
        notificationService.sendTempPasswordToUser(user, password);
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);
    }

    private String generatePassword(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;

    }
}
