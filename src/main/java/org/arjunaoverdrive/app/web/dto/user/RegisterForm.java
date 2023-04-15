package org.arjunaoverdrive.app.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterForm {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    @Override
    public String toString() {
        return "RegisterForm{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
