package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.DAO.UserRepository;
import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.security.ApplicationUserRole;
import org.arjunaoverdrive.app.security.SecurityUser;
import org.arjunaoverdrive.app.web.DTO.user.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserById(long id) {
        User user = Optional.ofNullable(userRepository.findUserById(id))
                .orElseThrow(() -> new RuntimeException("User with this id: " + id + " doesn't exist"));
        return user;
    }

    @Override
    public long save(RegisterForm formData) {
        User user = new User(formData.getFirstName(),
                formData.getLastName(),
                formData.getEmail(),
                passwordEncoder.encode(formData.getPassword()),
                getRoleFromFormData(formData.getRole()),
                true);
        return userRepository.save(user).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return SecurityUser.fromUser(user);
    }

    private ApplicationUserRole getRoleFromFormData(String formDataRole){
        ApplicationUserRole[] values = ApplicationUserRole.values();

        return Arrays.stream(values)
                .filter(userRole -> formDataRole.equalsIgnoreCase(userRole.name()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Role not found " + formDataRole));
    }

}
