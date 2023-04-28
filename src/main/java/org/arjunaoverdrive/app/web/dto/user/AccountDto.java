package org.arjunaoverdrive.app.web.dto.user;

import lombok.Data;
import org.arjunaoverdrive.app.model.User;

@Data
public class AccountDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public AccountDto() {
    }

    public AccountDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}
