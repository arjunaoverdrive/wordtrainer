package org.arjunaoverdrive.app.services.notifications;

import org.arjunaoverdrive.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class NotificationServiceImpl implements NotificationService {


    private final JavaMailSender mailSender;

    @Autowired
    public NotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendTempPasswordToUser(User user, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@wordtrainer.org");
        message.setTo(user.getEmail());
        message.setSubject("WordTrainer App temporary password");
        message.setText("Here's your password: \n" + password +
                "\n");

        mailSender.send(message);
    }
}
