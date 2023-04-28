package org.arjunaoverdrive.app.web.dto.user;

public class PasswordRestoreStatus {
    private  String message;
    private boolean status;

    public PasswordRestoreStatus() {
    }

    public PasswordRestoreStatus(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
