package org.arjunaoverdrive.app.services.user;

import org.arjunaoverdrive.app.web.dto.user.PasswordRestoreStatus;

public interface RestorePasswordService {
    PasswordRestoreStatus restorePasswordForUser(String email);
}
