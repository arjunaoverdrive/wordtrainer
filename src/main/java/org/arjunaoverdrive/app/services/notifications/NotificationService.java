package org.arjunaoverdrive.app.services.notifications;

import org.arjunaoverdrive.app.model.User;

public interface NotificationService {

    void sendTempPasswordToUser(User user, String password);
}
