package org.arjunaoverdrive.app.services.user;

import org.arjunaoverdrive.app.dao.UserRepository;
import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.security.ApplicationUserRole;
import org.arjunaoverdrive.app.security.SecurityUser;
import org.arjunaoverdrive.app.web.dto.user.AccountDto;
import org.arjunaoverdrive.app.web.dto.user.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserById(long id) {
        User user = Optional.ofNullable(userRepository.findUserById(id))
                .orElseThrow(() -> new RuntimeException("User with this id: " + id + " doesn't exist"));
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = Optional.ofNullable(userRepository.findUserByEmail(email))
                .orElseThrow(() -> new RuntimeException("User with this email is not found " + email));
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
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserFromSecurityContext() {
        org.springframework.security.core.userdetails.User user =  (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User appUser = Optional.ofNullable(userRepository.findUserByEmail(user.getUsername()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + user.getUsername()));
        return appUser;
    }

    @Override
    public User saveChanges(Integer id, AccountDto dto) {
        User user = findUserById(id);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        String password = user.getPassword().equals(dto.getPassword()) ?
                user.getPassword() : passwordEncoder.encode(dto.getPassword());
        user.setPassword(password);

        return userRepository.save(user);
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
