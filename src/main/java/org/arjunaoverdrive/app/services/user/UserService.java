package org.arjunaoverdrive.app.services.user;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.web.DTO.user.RegisterForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    long save(RegisterForm formData);

    User getUserFromSecurityContext();
}
